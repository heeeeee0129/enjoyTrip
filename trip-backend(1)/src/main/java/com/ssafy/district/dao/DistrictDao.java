package com.ssafy.district.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.district.dto.Sido;

@Mapper
public interface DistrictDao {
	
	public List<Sido> getSidos();
	
	public List<String> getGuguns(int sidoCode);
}
