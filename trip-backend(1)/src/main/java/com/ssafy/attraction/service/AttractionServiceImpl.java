package com.ssafy.attraction.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.attraction.dao.AttractionDao;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionWithOverview;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private AttractionDao attractionDao;
	
	public AttractionServiceImpl(AttractionDao attractionDao) {
		super();
		this.attractionDao = attractionDao;
	}

	@Override
	public AttractionWithOverview getAttractionById(int attractionId) throws Exception {
		return attractionDao.getAttractionById(attractionId);
	}

	@Override
	public List<Attraction> getAllAttractions(int sido, int gugun, String keyword, String[] contentTypeIds) throws Exception {
		return attractionDao.getAllAttractions(sido, gugun, keyword, contentTypeIds);
	}

	@Override
	public List<Attraction> getUserAttraction(String userId) throws SQLException {
		if(userId == null) {
			return new ArrayList<Attraction>();
		}
		return attractionDao.getUserAttraction(userId);
	}
	
}
