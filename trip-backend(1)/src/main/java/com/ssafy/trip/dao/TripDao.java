package com.ssafy.trip.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.dto.Trip;

@Mapper
public interface TripDao {

	int addPlan(Trip trip);
	
	int deleteAll(String userId);

	int deletePlan(int contentId);
}
