package com.ssafy.district.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ssafy.district.dto.Sido;
import com.ssafy.district.service.DistrictService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/districtapi")
public class DistrictController {
	
	private DistrictService districtService;
	
	public DistrictController(DistrictService districtService) {
		super();
		this.districtService = districtService;
	}
	
	@Operation(summary = "시/도 정보 얻어오기")
	@GetMapping("/sido")
	public ResponseEntity<?> getSido(){
		try {
			List<Sido> sidos = districtService.getSidos();
			if(!(sidos.isEmpty())) {
				return ResponseEntity.ok(sidos);
			}else {
				return ResponseEntity.noContent().build();
			}
			
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "구/군 정보 얻어오기")
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<?> getGugun(@PathVariable int sidoCode){
		try {
			List<String> guguns = districtService.getGuguns(sidoCode);
			if(!(guguns.isEmpty())) {
				return ResponseEntity.ok(guguns);
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