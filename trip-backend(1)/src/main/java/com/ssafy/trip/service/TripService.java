package com.ssafy.trip.service;

import com.ssafy.trip.dto.Trip;

public interface TripService {
	public int addPlan(Trip trip);
	
	public int deletePlan(int contentId);
	
	public int deleteAll(String userId);
}
