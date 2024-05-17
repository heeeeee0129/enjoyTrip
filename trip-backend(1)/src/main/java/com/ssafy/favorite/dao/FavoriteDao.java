package com.ssafy.favorite.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.favorite.dto.Favorite;

@Mapper
public interface FavoriteDao {

	List<Favorite> listArticle(String userId) throws SQLException;

	int writeArticle(Favorite favorite) throws SQLException;

	int deleteArticle(Favorite favorite) throws SQLException;

	int checkArticle(Favorite favorite) throws SQLException;

	int countArticle(int hotNo) throws SQLException;

}
