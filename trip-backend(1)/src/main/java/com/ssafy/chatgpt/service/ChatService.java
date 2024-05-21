package com.ssafy.chatgpt.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.chatgpt.dto.ChatGPT;

public interface ChatService {
	ResponseEntity<?> getAssistantMsg(ChatGPT chatGPT) throws JsonProcessingException;
}
