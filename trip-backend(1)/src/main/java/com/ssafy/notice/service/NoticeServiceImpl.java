package com.ssafy.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.notice.dao.NoticeDao;
import com.ssafy.notice.dto.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;

	public NoticeServiceImpl(NoticeDao noticeDao) {
		super();
		this.noticeDao = noticeDao;
	}

	@Override
	public int writeArticle(Notice notice) throws Exception {
		return noticeDao.writeArticle(notice);
	}

	@Override
	public List<Notice> listArticle() throws Exception {
		return noticeDao.listArticle();
	}

	@Override
	public Notice getArticle(int articleNo) throws Exception {
		return noticeDao.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		noticeDao.updateHit(articleNo);
	}

	@Override
	public int modifyArticle(Notice notice) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		return noticeDao.modifyArticle(notice);
	}

	@Override
	public int deleteArticle(int articleNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		return noticeDao.deleteArticle(articleNo);
	}

}
