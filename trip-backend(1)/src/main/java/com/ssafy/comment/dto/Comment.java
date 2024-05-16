package com.ssafy.comment.dto;

public class Comment {
	private int replyNo;
	private int articleNo;
	private String userId;
	private String userName;
	private String content;
	private String registerTime;
	private int parentReplyNo;

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	
	public int getParentReplyNo() {
		return parentReplyNo;
	}

	public void setParentReplyNo(int parentReplyNo) {
		this.parentReplyNo = parentReplyNo;
	}

	@Override
	public String toString() {
		return "Comment [replyNo=" + replyNo + ", articleNo=" + articleNo + ", userId=" + userId + ", userName="
				+ userName + ", content=" + content + ", registerTime=" + registerTime + ", parentReplyNo="
				+ parentReplyNo + "]";
	}

}
