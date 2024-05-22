package com.ssafy.chatgpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.chatgpt.dto.ChatGPT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatServiceImpl implements ChatService {

	@Value("${openai.api.key}")
	private String apiKey;

	public JsonNode callChatGpt(String userMsg) throws JsonProcessingException {
		final String url = "https://api.openai.com/v1/chat/completions";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> bodyMap = new HashMap<>();
//      bodyMap.put("model", "gpt-4");
		bodyMap.put("model", "gpt-3.5-turbo");

		List<Map<String, String>> messages = new ArrayList<>();
		Map<String, String> userMessage = new HashMap<>();
		userMessage.put("role", "user");
		userMessage.put("content", userMsg);
		messages.add(userMessage);

		Map<String, String> assistantMessage = new HashMap<>();
		assistantMessage.put("role", "system");
		assistantMessage.put("content",
				"너는 여행지 주변 맛집을 추천해주는 API야. 너의 역할은 사용자가 제공한 위치 정보를 기반으로 신뢰할 수 있는 맛집을 추천해주는 것이다.  사용자가 음식 종류를 말하지 않더라도 알아서 맛집을 추천해줘야한다. 사용자가 음식 종류를 말하면 음식 종류에 맞는 맛집을 추천해줘야한다. 추천할 때는 식당 이름, 주소, 주요 메뉴 및 가격, 리뷰, 평점 등을 포함해야 한다. 또한 사용자에게 추가적인 정보나 요청 사항이 있는지 물어보고, 필요시 추가적인 추천도 제공해야 한다.");
		messages.add(assistantMessage);

		bodyMap.put("messages", messages);

		String body = objectMapper.writeValueAsString(bodyMap);

		HttpEntity<String> request = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		return objectMapper.readTree(response.getBody());
	}

	@Override
	public ResponseEntity<?> getAssistantMsg(ChatGPT chatGPT) throws JsonProcessingException {
		JsonNode jsonNode = callChatGpt(chatGPT.getUserMsg());
		String content = jsonNode.path("choices").get(0).path("message").path("content").asText();
		ChatGPT response = new ChatGPT();
		response.setGptMsg(content);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
