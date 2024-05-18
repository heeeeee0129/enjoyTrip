package com.ssafy.district.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.district.dto.Address;
import com.ssafy.district.dto.Sido;

public interface DistrictService {
	
	public List<Sido> getSidos();
	
	public List<String> getGuguns(int sidoCode);

	public String getGugunName(Address address) throws SQLException;

	public String getSidoName(int sidocode) throws SQLException;
}
