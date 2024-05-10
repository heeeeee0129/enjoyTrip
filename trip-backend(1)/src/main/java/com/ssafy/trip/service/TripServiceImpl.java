package com.ssafy.trip.service;

import org.springframework.stereotype.Service;

import com.ssafy.trip.dao.TripDao;
import com.ssafy.trip.dto.Trip;

@Service
public class TripServiceImpl implements TripService {

	private TripDao tripDao;
	
	public TripServiceImpl(TripDao tripDao) {
		super();
		this.tripDao = tripDao;
	}

	public int addPlan(Trip trip) {
		return tripDao.addPlan(trip);
	}

	public int deletePlan(int contentId) {
		return tripDao.deletePlan(contentId);
	}

	@Override
	public int deleteAll(String userId) {
		return tripDao.deleteAll(userId);
	}
}
