package com.ssafy.chatgpt.dto;

public class ChatGPT {
	private String userMsg;
	private String gptMsg;

	public String getUserMsg() {
		return userMsg;
	}

	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}

	public String getGptMsg() {
		return gptMsg;
	}

	public void setGptMsg(String gptMsg) {
		this.gptMsg = gptMsg;
	}

	@Override
	public String toString() {
		return "ChatGPT [userMsg=" + userMsg + ", gptMsg=" + gptMsg + "]";
	}

}
