package com.ssafy.algorithm.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.algorithm.dao.Slang;

@Service
public class SlangServiceImpl implements SlangService {

	private Slang slang;
	
	public SlangServiceImpl(Slang slang) {
		super();
		this.slang = slang;
	}

	@Override
	public List<String> getList() throws SQLException {
		return slang.getList();
	}
}
