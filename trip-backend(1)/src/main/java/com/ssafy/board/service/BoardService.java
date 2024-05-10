package com.ssafy.board.service;

import java.util.Map;

import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardListDto;


public interface BoardService {
	
	int writeArticle(Board boardDto) throws Exception;
	
	BoardListDto listArticle(Map<String, String> map) throws Exception;
	
	Board getArticle(int articleNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	
	int modifyArticle(Board boardDto) throws Exception;
	
	int deleteArticle(int articleNo) throws Exception;

	int deleteAll(String id) throws Exception;
}
