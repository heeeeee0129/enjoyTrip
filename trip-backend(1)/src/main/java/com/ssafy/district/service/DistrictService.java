package com.ssafy.district.service;

import java.util.List;

import com.ssafy.district.dto.Sido;

public interface DistrictService {
	
	public List<Sido> getSidos();
	
	public List<String> getGuguns(int sidoCode);
}
