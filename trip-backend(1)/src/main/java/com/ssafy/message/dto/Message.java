package com.ssafy.message.dto;

public class Message {
	private int messageNo;
	private String fromId;
	private String toId;
	private String fromName;
	private String toName;
	private String title;
	private String content;
	private String createdAt;
	private boolean readed;

	public int getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	@Override
	public String toString() {
		return "Message [messageNo=" + messageNo + ", fromId=" + fromId + ", toId=" + toId + ", fromName=" + fromName
				+ ", toName=" + toName + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
				+ ", readed=" + readed + "]";
	}

}
