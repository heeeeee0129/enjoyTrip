package com.ssafy.district.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.district.dto.Address;
import com.ssafy.district.dto.Sido;

@Mapper
public interface DistrictDao {
	
	public List<Sido> getSidos();
	
	public List<String> getGuguns(int sidoCode);

	public String getGugunName(Address address) throws SQLException;
	
	public String getSidoName(int sidocode) throws SQLException;
}
