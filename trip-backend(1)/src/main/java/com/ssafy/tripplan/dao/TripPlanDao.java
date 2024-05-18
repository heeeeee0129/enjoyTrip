package com.ssafy.tripplan.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.tripplan.dto.TripLocation;
import com.ssafy.tripplan.dto.TripPlan;

@Mapper
public interface TripPlanDao {

	List<TripPlan> listArticle() throws SQLException;

	TripPlan getArticle(int id) throws SQLException;
	
	int writeLocation(TripPlan tripplan) throws SQLException;

	int deleteArticle(int id) throws SQLException;

	List<TripLocation> getLocation(int id) throws SQLException;

	int writeArticle(TripPlan tripplan) throws SQLException;

	int getId(TripPlan tripplan) throws SQLException;

	List<TripPlan> listUserArticle(String userId) throws SQLException;

}
