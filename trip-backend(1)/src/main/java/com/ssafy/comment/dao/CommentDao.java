package com.ssafy.comment.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.dto.Comment;

@Mapper
public interface CommentDao {

	List<Comment> listComment(int articleNo) throws SQLException ;

	int writeComment(Comment comment) throws SQLException ;

	int modifyComment(int replyNo) throws SQLException ;

	int deleteComment(int replyNo) throws SQLException ;

	int deleteAll(String userId) throws SQLException ;

}
