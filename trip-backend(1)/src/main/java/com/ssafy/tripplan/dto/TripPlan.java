package com.ssafy.tripplan.dto;

import java.util.List;

public class TripPlan {
	private int id;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private String userId;
	private String userName;
	private String createdAt;
	private List<TripLocation> tripLocations;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public List<TripLocation> getTripLocations() {
		return tripLocations;
	}
	public void setTripLocations(List<TripLocation> tripLocations) {
		this.tripLocations = tripLocations;
	}
	@Override
	public String toString() {
		return "TripPlan [id=" + id + ", title=" + title + ", content=" + content + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", userId=" + userId + ", userName=" + userName + ", createdAt=" + createdAt
				+ ", tripLocations=" + tripLocations + "]";
	}

}
