package com.ssafy.hotplace.dto;

public class HotPlace {
	private int hotNo;
	private String userId;
	private String userName;
	private String placeName;
	private String category;
	private String content;
	private int hit;
	private String registerTime;
	private double latitude;
	private double longitude;
	private FileInfo fileInfo;

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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public String toString() {
		return "HotPlace [hotNo=" + hotNo + ", userId=" + userId + ", userName=" + userName + ", placeName=" + placeName
				+ ", category=" + category + ", content=" + content + ", hit=" + hit + ", registerTime=" + registerTime
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", fileInfo=" + fileInfo + "]";
	}

}
