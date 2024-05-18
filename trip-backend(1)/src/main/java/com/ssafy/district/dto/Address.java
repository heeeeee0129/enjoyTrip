package com.ssafy.district.dto;

public class Address {
	private int sidoCode;
	private int gugunCode;

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	@Override
	public String toString() {
		return "Address [sidoCode=" + sidoCode + ", gugunCode=" + gugunCode + "]";
	}

}
