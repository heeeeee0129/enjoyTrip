package com.ssafy.attraction.dto;

public class AttractionWithOverview extends Attraction{
	private String overview;
	
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return super.toString() + ", overview=" + overview + "]";
    }

}
