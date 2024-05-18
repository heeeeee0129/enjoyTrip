package com.ssafy.friend.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.friend.dao.FriendDao;
import com.ssafy.friend.dto.Friend;

@Service
public class FriendServiceImpl implements FriendService {

	private FriendDao friendDao;

	public FriendServiceImpl(FriendDao friendDao) {
		super();
		this.friendDao = friendDao;
	}

	@Override
	public List<Friend> listFriend(String userId) throws SQLException {
		return friendDao.listFriend(userId);
	}

	@Override
	public int addFriend(Friend friend) throws SQLException {
		return friendDao.addFriend(friend);
	}

	@Override
	public int deleteFriend(Friend friend) throws SQLException {
		return friendDao.deleteFriend(friend);
	}

	@Override
	public int getFriend(Friend friend) throws SQLException {
		return friendDao.getFriend(friend);
	}

}
