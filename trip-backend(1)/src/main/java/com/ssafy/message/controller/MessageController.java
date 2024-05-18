package com.ssafy.message.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.message.service.MessageService;

@RestController
@RequestMapping("/messageapi")
public class MessageController {
	
	private MessageService messageService;

	public MessageController(MessageService messageService) {
		super();
		this.messageService = messageService;
	}
	
	
}
