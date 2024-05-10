package com.ssafy.attraction.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionWithOverview;

public interface AttractionService {
    
    List<Attraction> getAllAttractions(int sido, int gugun, String keyword, String[] contentTypeIds) throws Exception;
	List<Attraction> getUserAttraction(String userId) throws SQLException;
    AttractionWithOverview getAttractionById(int attractionId) throws Exception;

}
