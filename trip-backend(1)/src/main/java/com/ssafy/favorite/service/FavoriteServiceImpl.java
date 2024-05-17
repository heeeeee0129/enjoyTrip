package com.ssafy.favorite.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.favorite.dao.FavoriteDao;
import com.ssafy.favorite.dto.Favorite;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	private FavoriteDao favoriteDao;
	

	public FavoriteServiceImpl(FavoriteDao favoriteDao) {
		super();
		this.favoriteDao = favoriteDao;
	}

	@Override
	public List<Favorite> listArticle(String userId) throws SQLException {
		return favoriteDao.listArticle(userId);
	}

	@Override
	public int wrtieArticle(Favorite favorite) throws SQLException {
		return favoriteDao.writeArticle(favorite);
	}

	@Override
	public int deleteArticle(Favorite favorite) throws SQLException {
		return favoriteDao.deleteArticle(favorite);
	}

	@Override
	public int checkArticle(Favorite favorite) throws SQLException{
		return favoriteDao.checkArticle(favorite);
	}

	@Override
	public int countArticle(int hotNo) throws SQLException {
		return favoriteDao.countArticle(hotNo);
	}

}
