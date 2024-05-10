package com.ssafy.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.service.AttractionService;
import com.ssafy.district.dto.Sido;
import com.ssafy.district.service.DistrictService;
import com.ssafy.trip.dto.Trip;
import com.ssafy.trip.service.TripService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/tripapi")
public class TripController {

	private final DistrictService districtService;
	private final TripService tripService;
	private final AttractionService attractionService;

	public TripController(DistrictService districtService, TripService tripService,
			AttractionService attractionService) {
		super();
		this.districtService = districtService;
		this.tripService = tripService;
		this.attractionService = attractionService;
	}

	@Operation(summary = "여행 계획 삭제")
	@DeleteMapping("/trip/{contentId}")
	public ResponseEntity<?> deletePlan(@PathVariable(value = "contentId") int contentId) {
		try {
			int cnt = tripService.deletePlan(contentId);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "여행 계획 추가")
	@PostMapping("/trip")
	private ResponseEntity<?> addPlan(@RequestBody Trip trip) {
		try {
			tripService.addPlan(trip);
			Attraction addedAttraction = new Attraction();
			addedAttraction = attractionService.getAttractionById(trip.getContentId());
			ObjectMapper mapper = new ObjectMapper();
			return ResponseEntity.ok(mapper.writeValueAsString(addedAttraction));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "여행 계획 가져오기")
	@GetMapping("/trip/{userId}")
	private ResponseEntity<?> getPlan(@PathVariable(value = "userId") String userId) {
		try {
			
			List<Attraction> attractions = attractionService.getUserAttraction(userId);
		
			return ResponseEntity.ok(attractions);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "여행 계획 검색창 가져오기 ???")
	@GetMapping("/trip")
	private ResponseEntity<?> mySearch() {
		try {
			List<Sido> sidos = districtService.getSidos();
			return ResponseEntity.ok(sidos);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}
}
