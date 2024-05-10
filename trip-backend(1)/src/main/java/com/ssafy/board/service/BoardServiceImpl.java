package com.ssafy.board.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.board.dao.BoardDao;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardListDto;

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
	public BoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");
		List<Board> list = boardDao.listArticle(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = boardDao.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
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
	public int deleteAll(String id) throws SQLException {
		return boardDao.deleteAll(id);
	}

}
