package com.ssafy.friend.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.friend.dto.Friend;

@Mapper
public interface FriendDao {

	List<Friend> listFriend(String userId) throws SQLException;

	int addFriend(Friend friend) throws SQLException;

	int deleteFriend(Friend friend) throws SQLException;
	
	int getFriend(Friend friend) throws SQLException;

}
