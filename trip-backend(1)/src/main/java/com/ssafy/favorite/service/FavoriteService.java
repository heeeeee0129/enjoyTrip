package com.ssafy.favorite.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.favorite.dto.Favorite;

public interface FavoriteService {

	List<Favorite> listArticle(String userId) throws SQLException;

	int wrtieArticle(Favorite favorite) throws SQLException;

	int deleteArticle(Favorite favorite) throws SQLException;

	int checkArticle(Favorite favorite) throws SQLException;

	int countArticle(int hotNo) throws SQLException;

}
