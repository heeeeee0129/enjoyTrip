package com.ssafy.attraction.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionWithOverview;

@Mapper
public interface AttractionDao {
	List<Attraction> getAllAttractions(int sido, int gugun, String keyword, String[] contentTypeIds) throws SQLException;
	AttractionWithOverview getAttractionById(int attractionId) throws SQLException;
	List<Attraction> getUserAttraction(String userId) throws SQLException;
	List<Attraction> suggestAttractions(int categoryNo) throws SQLException;
	List<Attraction> getTopList() throws SQLException;
}