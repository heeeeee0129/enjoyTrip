package com.ssafy.hotplace.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hotplace.dto.HotPlace;

@Mapper
public interface HotPlaceDao {

	void writeArticle(HotPlace hotplace) throws SQLException;

	int registerFile(HotPlace hotplace) throws SQLException;

	List<HotPlace> listArticle() throws SQLException;

	void updateHit(int hotNo) throws SQLException;

	HotPlace getArticle(int hotNo) throws SQLException;

	int modifyArticle(HotPlace hotplace) throws SQLException;
	
	int modifyFile(HotPlace hotplace) throws SQLException;

	int deleteArticle(int hotNo) throws SQLException;
	
}