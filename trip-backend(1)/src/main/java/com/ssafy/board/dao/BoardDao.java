package com.ssafy.board.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.dto.Board;

@Mapper
public interface BoardDao {

	int writeArticle(Board board) throws SQLException ;
	List<Board> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	Board getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	int modifyArticle(Board board) throws SQLException;
	int deleteArticle(int articleNO) throws SQLException;
	int deleteAll(String id) throws SQLException;
	
}
