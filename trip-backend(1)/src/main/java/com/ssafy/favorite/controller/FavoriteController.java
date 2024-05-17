package com.ssafy.favorite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.favorite.dto.Favorite;
import com.ssafy.favorite.service.FavoriteService;


import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/favoriteapi")
public class FavoriteController {

	private FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		super();
		this.favoriteService = favoriteService;
	}
	
	@Operation(summary = "즐겨찾기 개수 가져오기 ")
	@GetMapping("/favorite/count/{hotNo}")
	public ResponseEntity<?> countArticle(@PathVariable(value = "hotNo") int hotNo) {
		try {
			int cnt = favoriteService.countArticle(hotNo);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "즐겨찾기 정보 체크")
	@PostMapping("/favorite/check")
	public ResponseEntity<?> getArticle(@RequestBody Favorite favorite) {
		try {
			int cnt = favoriteService.checkArticle(favorite);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "등록된 모든 즐겨찾기 정보를 가져온다.")
	@GetMapping("/favorite/{userId}")
	public ResponseEntity<?> listArticle(@PathVariable(value ="userId") String userId) {
		try {
			List<Favorite> favorites = favoriteService.listArticle(userId);
			if (!favorites.isEmpty()) {
				return ResponseEntity.ok(favorites);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "즐겨찾기 정보를 저장한다.")
	@PostMapping("/favorite")
	public ResponseEntity<?> wrtieArticle(@RequestBody Favorite favorite) {
		try {
			int cnt = favoriteService.wrtieArticle(favorite);
			if (cnt != 0) {
				return ResponseEntity.ok(cnt);
			} else {
				return ResponseEntity.internalServerError().body("Sorry");
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/favorite/delete")
	public ResponseEntity<?> deleteArticle(@RequestBody Favorite favorite) {
		try {
			int cnt = favoriteService.deleteArticle(favorite);
			if (cnt != 0) {
				return ResponseEntity.ok(cnt);
			} else {
				return ResponseEntity.internalServerError().body("Sorry");
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}
}
