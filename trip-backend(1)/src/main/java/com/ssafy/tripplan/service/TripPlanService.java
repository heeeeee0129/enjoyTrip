package com.ssafy.tripplan.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.tripplan.dto.TripLocation;
import com.ssafy.tripplan.dto.TripPlan;

public interface TripPlanService {

	List<TripPlan> listArticle() throws SQLException;

	TripPlan getArticle(int id) throws SQLException;

	int writeArticle(TripPlan tripplan) throws SQLException;

	int deleteArticle(int id) throws SQLException;

	List<TripLocation> getLocation(int id) throws SQLException;

	List<TripPlan> listUserArticle(String userId) throws SQLException;

}
