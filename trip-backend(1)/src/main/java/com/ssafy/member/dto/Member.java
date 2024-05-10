package com.ssafy.member.dto;

public class Member {

	private String name;
	private String id;
	private String pwd;
	private String emailId;
	private String emailDomain;
	private String sido;
	private String gugun;
	
	public Member() {};
	
	public Member(String name, String id, String pwd, String emailId, String emailDomain, String sido, String gugun) {
		super();
		this.name = name;
		this.id = id;
		this.pwd = pwd;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.sido = sido;
		this.gugun = gugun;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	@Override
	public String toString() {
		return "Member [name=" + name + ", id=" + id + ", pwd=" + pwd + ", emailId=" + emailId + ", emailDomain="
				+ emailDomain + ", sido=" + sido + ", gugun=" + gugun + "]";
	}
}
