package com.ssafy.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.board.dao.BoardDao;
import com.ssafy.board.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao;

	public BoardServiceImpl(BoardDao boardDao) {
		super();
		this.boardDao = boardDao;
	}

	@Override
	public int writeArticle(Board boardDto) throws Exception {
		return boardDao.writeArticle(boardDto);
	}

	@Override
	public List<Board> listArticle() throws Exception {
		return boardDao.listArticle();
	}

	@Override
	public Board getArticle(int articleNo) throws Exception {
		return boardDao.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardDao.updateHit(articleNo);
	}

	@Override
	public int modifyArticle(Board boardDto) throws Exception {
		return boardDao.modifyArticle(boardDto);

	}

	@Override
	public int deleteArticle(int articleNo) throws Exception {
		return boardDao.deleteArticle(articleNo);
	}

	@Override
	public int deleteAll(String id) {
		return boardDao.deleteAll(id);
	}

}
