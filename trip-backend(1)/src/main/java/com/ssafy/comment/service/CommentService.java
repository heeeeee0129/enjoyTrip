package com.ssafy.comment.service;

import java.util.List;

import com.ssafy.comment.dto.Comment;

public interface CommentService {
	List<Comment> listComment(int articleNo) throws Exception;

	int writeComment(Comment comment) throws Exception;

	int modifyComment(int replyNo) throws Exception;

	int deleteComment(int replyNo) throws Exception;

	int deleteAll(String userId) throws Exception;
}
