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
	public int modifyComment(Comment comment) throws Exception {
		return commentDao.modifyComment(comment);
	}

	@Override
	public int deleteComment(int replyNo) throws Exception {
		return commentDao.deleteComment(replyNo);
	}

	@Override
	public Comment getComment(int replyNo) throws Exception {
		return commentDao.getComment(replyNo);
	}

	@Override
	public List<Comment> listReComment(int replyNo) throws Exception {
		return commentDao.listReComment(replyNo);
	}

	@Override
	public int getCountComment(int articleNo) throws Exception {
		return commentDao.getCountComment(articleNo);
	}

}
