package com.ssafy.tripplan.dto;

public class TripLocation {
	private int id;
	private int routeId;
	private String memo;
	private int idx;
	private int contentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "TripLocation [id=" + id + ", routeId=" + routeId + ", memo=" + memo + ", idx=" + idx + ", contentId="
				+ contentId + "]";
	}

}