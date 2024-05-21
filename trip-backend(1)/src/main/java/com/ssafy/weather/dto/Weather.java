package com.ssafy.weather.dto;

public class Weather {
	private double lat;
	private double lon;
	private double currentTemp;
	private double minTemp;
	private double maxTemp;
	private int humidity;
	private double windSpeed;
	private String sunrise;
	private String sunset;
	private String icon;
	private String state;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(double currentTemp) {
		this.currentTemp = currentTemp;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Weather [lat=" + lat + ", lon=" + lon + ", currentTemp=" + currentTemp + ", minTemp=" + minTemp
				+ ", maxTemp=" + maxTemp + ", humidity=" + humidity + ", windSpeed=" + windSpeed + ", sunrise="
				+ sunrise + ", sunset=" + sunset + ", icon=" + icon + ", state=" + state + "]";
	}

}
