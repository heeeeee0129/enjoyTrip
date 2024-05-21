package com.ssafy.chatgpt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.chatgpt.dto.ChatGPT;
import com.ssafy.chatgpt.service.ChatService;

@RestController
@RequestMapping("/chatgptapi")
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/propmt")
	public ResponseEntity<?> getAssistantMsg(@RequestBody ChatGPT chatGPT) {
		try {
			return chatService.getAssistantMsg(chatGPT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}
}
