package com.ssafy.favorite.dto;

public class Favorite {
	private int favoriteNo;
	private int hotNo;
	private String userId;
	private String userName;

	public int getFavoriteNo() {
		return favoriteNo;
	}

	public void setFavoriteNo(int favoriteNo) {
		this.favoriteNo = favoriteNo;
	}

	public int getHotNo() {
		return hotNo;
	}

	public void setHotNo(int hotNo) {
		this.hotNo = hotNo;
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

	@Override
	public String toString() {
		return "Favorite [favoriteNo=" + favoriteNo + ", hotNo=" + hotNo + ", userId=" + userId + ", userName="
				+ userName + "]";
	}

}
