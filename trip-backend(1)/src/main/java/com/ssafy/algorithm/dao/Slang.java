package com.ssafy.algorithm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Slang {
	public List<String> getList();
}
