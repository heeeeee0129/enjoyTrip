package com.ssafy.friend.dto;

public class Friend {
	private int friendNo;
	private String userId;
	private String userName;
	private String friendId;
	private String friendName;

	public int getFriendNo() {
		return friendNo;
	}

	public void setFriendNo(int friendNo) {
		this.friendNo = friendNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	@Override
	public String toString() {
		return "Friend [friendNo=" + friendNo + ", userId=" + userId + ", userName=" + userName + ", friendId="
				+ friendId + ", friendName=" + friendName + "]";
	}

}
