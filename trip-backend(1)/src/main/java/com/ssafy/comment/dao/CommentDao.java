package com.ssafy.comment.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.dto.Comment;

@Mapper
public interface CommentDao {

	List<Comment> listComment(int articleNo) throws SQLException;
	
	Comment getComment(int replyNo) throws SQLException;

	int writeComment(Comment comment) throws SQLException;

	int modifyComment(Comment comment) throws SQLException;

	int deleteComment(int replyNo) throws SQLException;

	List<Comment> listReComment(int replyNo) throws SQLException;

	int getCountComment(int articleNo) throws SQLException;
}
