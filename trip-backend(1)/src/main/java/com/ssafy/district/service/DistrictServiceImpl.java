package com.ssafy.district.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.district.dao.DistrictDao;
import com.ssafy.district.dto.Address;
import com.ssafy.district.dto.Sido;

@Service
public class DistrictServiceImpl implements DistrictService{
	
	
	private final DistrictDao districtDao;
	
	public DistrictServiceImpl(DistrictDao districtDao) {
		super();
		this.districtDao = districtDao;
	}

	public List<Sido> getSidos() {
		return districtDao.getSidos();
	}

	public List<String> getGuguns(int sidoCode) {
		return districtDao.getGuguns(sidoCode);
	}

	@Override
	public String getGugunName(Address address) throws SQLException {
		return districtDao.getGugunName(address);
	}

	@Override
	public String getSidoName(int sidocode) throws SQLException {
		return districtDao.getSidoName(sidocode);
	}
}
