package com.ssafy.tripplan.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripplan.dao.TripPlanDao;
import com.ssafy.tripplan.dto.TripLocation;
import com.ssafy.tripplan.dto.TripPlan;

@Service
public class TripPlanServiceImpl implements TripPlanService {
	
	private TripPlanDao tripplanDao;
	
	public TripPlanServiceImpl(TripPlanDao tripplanDao) {
		super();
		this.tripplanDao = tripplanDao;
	}

	@Override
	public List<TripPlan> listArticle() throws SQLException {
		return tripplanDao.listArticle();
	}

	@Override
	public TripPlan getArticle(int id) throws SQLException {
		return tripplanDao.getArticle(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int writeArticle(TripPlan tripplan) throws SQLException {
		tripplanDao.writeArticle(tripplan);
		int id = tripplanDao.getId(tripplan);
		tripplan.setId(id);
		return tripplanDao.writeLocation(tripplan);
	}

	@Override
	public int deleteArticle(int id) throws SQLException {
		return tripplanDao.deleteArticle(id);
	}


	@Override
	public List<TripLocation> getLocation(int id) throws SQLException {
		return tripplanDao.getLocation(id);
	}

	@Override
	public List<TripPlan> listUserArticle(String userId) throws SQLException {
		return tripplanDao.listUserArticle(userId);
	}

}
