package com.ssafy.notice.service;

import java.util.List;

import com.ssafy.notice.dto.Notice;



public interface NoticeService {
	int writeArticle(Notice boardDto) throws Exception;
	List<Notice> listArticle() throws Exception;
	Notice getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	int modifyArticle(Notice boardDto) throws Exception;
	int deleteArticle(int articleNo) throws Exception;
}
