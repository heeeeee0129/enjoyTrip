package com.ssafy.board.service;

import java.util.List;

import com.ssafy.board.dto.Board;


public interface BoardService {
	
	int writeArticle(Board boardDto) throws Exception;
	
	List<Board> listArticle() throws Exception;
	
	Board getArticle(int articleNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	
	int modifyArticle(Board boardDto) throws Exception;
	
	int deleteArticle(int articleNo) throws Exception;

	int deleteAll(String id);
}
