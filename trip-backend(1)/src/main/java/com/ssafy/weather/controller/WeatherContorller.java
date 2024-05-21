package com.ssafy.weather.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ssafy.weather.dto.Weather;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/weatherapi")
public class WeatherContorller {

	@Value("${openweather.api.key}")
	private String key;

	@Operation(summary = "해당 지역 날씨 정보 반환")
	@PostMapping("/weather")
	public ResponseEntity<?> getWeather(@RequestBody Weather weather) {
		try {
			String APIUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + weather.getLat() + "&lon="
					+ weather.getLon() + "&appid=" + key;
			URL url = new URL(APIUrl);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-type", "application/json");

			BufferedReader bufferedReader;

			if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
				bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
			}

			// 가져온 데이터를 문자열로 읽어옴
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			// Parse the JSON response
			JSONObject jsonResponse = new JSONObject(sb.toString());

			// Access the required fields
			double currentTemp = jsonResponse.getJSONObject("main").getDouble("temp") - 273.15; // Kelvin to Celsius
			double minTemp = jsonResponse.getJSONObject("main").getDouble("temp_min") - 273.15;
			double maxTemp = jsonResponse.getJSONObject("main").getDouble("temp_max") - 273.15;
			int humidity = jsonResponse.getJSONObject("main").getInt("humidity");
			double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
			String sunrise = new java.text.SimpleDateFormat("HH:mm:ss")
					.format(new java.util.Date(jsonResponse.getJSONObject("sys").getLong("sunrise") * 1000));
			String sunset = new java.text.SimpleDateFormat("HH:mm:ss")
					.format(new java.util.Date(jsonResponse.getJSONObject("sys").getLong("sunset") * 1000));

			// Access weather array to get the icon code and main weather
			JSONArray weatherArray = jsonResponse.getJSONArray("weather");
			String state = weatherArray.getJSONObject(0).getString("main");
			String iconCode = weatherArray.getJSONObject(0).getString("icon");

			weather.setCurrentTemp(currentTemp);
			weather.setMinTemp(minTemp);
			weather.setMaxTemp(maxTemp);
			weather.setHumidity(humidity);
			weather.setWindSpeed(windSpeed);
			weather.setSunrise(sunrise);
			weather.setSunset(sunset);
			weather.setIcon(iconCode);
			weather.setState(state);

			bufferedReader.close();
			urlConnection.disconnect();
			return ResponseEntity.ok(weather);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
