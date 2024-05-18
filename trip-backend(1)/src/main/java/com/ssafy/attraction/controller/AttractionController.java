package com.ssafy.attraction.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionWithOverview;
import com.ssafy.attraction.service.AttractionService;
import com.ssafy.favorite.dto.Favorite;
import com.ssafy.favorite.service.FavoriteService;
import com.ssafy.hotplace.dto.HotPlace;
import com.ssafy.hotplace.service.HotPlaceService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/attractionapi")
public class AttractionController {

	private AttractionService attractionService;
	private HotPlaceService hotplaceService;
	private FavoriteService favoriteService;

	public AttractionController(AttractionService attractionService, HotPlaceService hotplaceService, FavoriteService favoriteService) {
		super();
		this.attractionService = attractionService;
		this.hotplaceService = hotplaceService;
		this.favoriteService = favoriteService;
	}

	@Operation(summary = "관광지 조회")
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam(required = false) Integer sido,
			@RequestParam(required = false) Integer gugun, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String[] contentTypeIds) {
		try {
			List<Attraction> list = attractionService.getAllAttractions(sido, gugun, keyword, contentTypeIds);
			if (!list.isEmpty()) {
				return ResponseEntity.ok(list);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@Operation(summary = "관광지 상세 조회")
	@GetMapping("/{contentId}")
	public ResponseEntity<?> detail(@PathVariable int contentId) {
		try {
			AttractionWithOverview attraction = attractionService.getAttractionById(contentId);
			if (attraction != null) {
				return ResponseEntity.ok(attraction);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "좋아요 기반 관광지 추천")
	@PostMapping("/suggest")
	public ResponseEntity<?> getSuggest(@RequestBody Favorite favorite) {	
		try {
			List<Favorite> favorites = favoriteService.listArticle(favorite.getUserId());
			List<HotPlace> hotplaces = new ArrayList<>();	
			for(int i=0; i<favorites.size(); i++) {
				HotPlace hotplace = hotplaceService.getArticle(favorites.get(i).getHotNo());
				hotplaces.add(hotplace);
			}
		
			Category[] list = new Category[8];
			list[0] = new Category(0, 12);
			list[1] = new Category(0, 14);
			list[2] = new Category(0, 15);
			list[3] = new Category(0, 25);
			list[4] = new Category(0, 28);
			list[5] = new Category(0, 32);
			list[6] = new Category(0, 38);
			list[7] = new Category(0, 39);
			
			for (int i = 0; i < hotplaces.size(); i++) {
				String category = hotplaces.get(i).getCategory();
				if ("관광지".equals(category)) {
					list[0].count++;
				}
				if ("문화시설".equals(category)) {
					list[1].count++;
				}
				if ("행사/공연/축제".equals(category)) {
					list[2].count++;
				}
				if ("여행코스".equals(category)) {
					list[3].count++;
				}
				if ("레포츠".equals(category)) {
					list[4].count++;
				}
				if ("숙박".equals(category)) {
					list[5].count++;
				}
				if ("쇼핑".equals(category)) {
					list[6].count++;
				}
				if ("음식점".equals(category)) {
					list[7].count++;
				}
			}
			Arrays.sort(list, (o1,o2) -> o2.count - o1.count);
			int categoryNo = list[0].categoryNo;
			List<Attraction> attractions = attractionService.suggestAttractions(categoryNo);
			return ResponseEntity.ok(attractions);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}

	private class Category {
		int count, categoryNo;

		public Category(int count, int categoryNo) {
			super();
			this.count = count;
			this.categoryNo = categoryNo;
		}

		@Override
		public String toString() {
			return "Category [count=" + count + ", categoryNo=" + categoryNo + "]";
		}

	}
}
