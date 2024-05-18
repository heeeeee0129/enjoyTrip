package com.ssafy.friend.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.friend.dto.Friend;

public interface FriendService {

	List<Friend> listFriend(String userId) throws SQLException;

	int addFriend(Friend friend) throws SQLException;

	int deleteFriend(Friend friend) throws SQLException;
	
	int getFriend(Friend friend) throws SQLException;

}
