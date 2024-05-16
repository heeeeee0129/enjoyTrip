package com.ssafy.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionWithOverview;
import com.ssafy.attraction.service.AttractionService;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;



@RestController
@RequestMapping("/attractionapi")
public class AttractionController {
	
	private AttractionService attractionService;
	
	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}

	@Operation(summary = "관광지 조회")
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam(required = false) Integer sido, 
            @RequestParam(required = false) Integer gugun, 
            @RequestParam(required = false) String keyword, 
            @RequestParam(required = false) String[] contentTypeIds){
		try {
			List<Attraction> list = attractionService.getAllAttractions(sido, gugun, keyword, contentTypeIds);
			if(!list.isEmpty()) {
				return ResponseEntity.ok(list);
			}else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@Operation(summary = "관광지 상세 조회")
	@GetMapping("/{contentId}")
	public ResponseEntity<?> detail(@PathVariable int contentId){
		try {
			AttractionWithOverview attraction = attractionService.getAttractionById(contentId);
			if(attraction != null) {
				return ResponseEntity.ok(attraction);
			}else {
				return ResponseEntity.noContent().build();
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}

	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}
}
