package com.ssafy.trip.dto;

public class Trip {
	private String userId;
	private int contentId;

	public Trip() {
		super();
	}

	public Trip(String userId, int contentId) {
		super();
		this.userId = userId;
		this.contentId = contentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "Trip [userId=" + userId + ", contentId=" + contentId + "]";
	}
}
