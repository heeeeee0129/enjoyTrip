package com.ssafy.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.dto.Board;

@Mapper
public interface BoardDao {

	int writeArticle(Board board);
	List<Board> listArticle();
	Board getArticle(int articleNo);
	void updateHit(int articleNo);
	
	int modifyArticle(Board board);
	int deleteArticle(int articleNO);
	int deleteAll(String id);
	
}
