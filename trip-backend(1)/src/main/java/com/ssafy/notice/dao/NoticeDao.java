package com.ssafy.notice.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.notice.dto.Notice;


@Mapper
public interface NoticeDao {
	int writeArticle(Notice notice) throws SQLException;
	List<Notice> listArticle() throws SQLException;
	Notice getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyArticle(Notice notice) throws SQLException;
	int deleteArticle(int articleNO) throws SQLException;
}
