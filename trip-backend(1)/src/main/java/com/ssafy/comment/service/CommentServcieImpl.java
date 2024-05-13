package com.ssafy.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.comment.dao.CommentDao;
import com.ssafy.comment.dto.Comment;

@Service
public class CommentServcieImpl implements CommentService {
	
	private CommentDao commentDao;
	
	public CommentServcieImpl(CommentDao commentDao) {
		super();
		this.commentDao = commentDao;
	}

	@Override
	public List<Comment> listComment(int articleNo) throws Exception {
		return commentDao.listComment(articleNo);
	}

	@Override
	public int writeComment(Comment comment) throws Exception {
		return commentDao.writeComment(comment);
	}

	@Override
	public int modifyComment(int replyNo) throws Exception {
		return commentDao.modifyComment(replyNo);
	}

	@Override
	public int deleteComment(int replyNo) throws Exception {
		return commentDao.deleteComment(replyNo);
	}

	@Override
	public int deleteAll(String userId) throws Exception {
		return commentDao.deleteAll(userId);
	}

}
