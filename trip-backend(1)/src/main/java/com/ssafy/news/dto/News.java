package com.ssafy.news.dto;

public class News {
	private String image;
	private String subject;
	private String url;

	public News() {
		super();
	}

	public News(String image, String subject, String url) {
		super();
		this.image = image;
		this.subject = subject;
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "News [image=" + image + ", subject=" + subject + ", url=" + url + "]";
	}

}
