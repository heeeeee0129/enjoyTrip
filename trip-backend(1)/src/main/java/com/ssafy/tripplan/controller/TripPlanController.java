package com.ssafy.tripplan.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



import com.ssafy.tripplan.dto.TripLocation;
import com.ssafy.tripplan.dto.TripPlan;
import com.ssafy.tripplan.service.TripPlanService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/tripplanapi")
public class TripPlanController {

	private TripPlanService tripplanService;

	public TripPlanController(TripPlanService tripplanService) {
		super();
		this.tripplanService = tripplanService;
	}
	
	
	@Operation(summary = "여행계획 글목록", description = "모든 여행계획 정보를 반환한다.")
	@GetMapping("/tripplan")
	public ResponseEntity<?> listArticle() {
		try {
			List<TripPlan> tripPlans = tripplanService.listArticle();
			return ResponseEntity.ok(tripPlans);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "여행계획 글목록", description = "해당 사용자의 여행계획 정보를 반환한다.")
	@GetMapping("/tripplan/user/{userId}")
	public ResponseEntity<?> listUserArticle(@PathVariable(value = "userId") String userId) {
		try {
			List<TripPlan> tripPlans = tripplanService.listUserArticle(userId);
			return ResponseEntity.ok(tripPlans);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "여행계획 글", description = "여행계획 정보를 반환한다.")
	@GetMapping("/tripplan/{id}")
	public ResponseEntity<?> getArticle(@PathVariable(value = "id") int id) {
		try {
			TripPlan tripPlan = tripplanService.getArticle(id);
			List<TripLocation> tripLocations = tripplanService.getLocation(id);
			tripPlan.setTripLocations(tripLocations);
			return ResponseEntity.ok(tripPlan);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "여행계획 작성 ")
	@PostMapping("/tripplan")
	public ResponseEntity<?> writeArticle(@RequestBody TripPlan tripplan) {
		try {
			int cnt = tripplanService.writeArticle(tripplan);
			if(cnt != 0) {
				return ResponseEntity.ok(cnt);
			}
			else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "여행계획 작성 ")
	@DeleteMapping("/tripplan")
	public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") int id) {
		try {
			int cnt = tripplanService.deleteArticle(id);
			if(cnt != 0) {
				return ResponseEntity.ok(cnt);
			}
			else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
