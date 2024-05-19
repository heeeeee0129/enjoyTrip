package com.ssafy.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.message.dto.Message;
import com.ssafy.message.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/messageapi")
public class MessageController {

	private MessageService messageService;
	private MemberService memberService;

	public MessageController(MessageService messageService, MemberService memberSerivce) {
		super();
		this.messageService = messageService;
		this.memberService = memberSerivce;
	}

	@Operation(summary = "해당 유저가 받은 모든 메시지를 가져온다.")
	@GetMapping("/message/recieve/{userId}")
	public ResponseEntity<?> getRecieveMessages(@PathVariable(value = "userId") String userId) {
		try {
			List<Message> messages = messageService.getRecieveMessages(userId);
			if (!messages.isEmpty()) {
				return ResponseEntity.ok(messages);
			} else
				return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "해당 유저가 보낸 모든 메시지를 가져온다.")
	@GetMapping("/message/send/{userId}")
	public ResponseEntity<?> getSendMessages(@PathVariable(value = "userId") String userId) {
		try {
			List<Message> messages = messageService.getSendMessages(userId);
			if (!messages.isEmpty()) {
				return ResponseEntity.ok(messages);
			} else
				return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "해당 메시지를 가져온다")
	@GetMapping("/message/{messageNo}")
	public ResponseEntity<?> getMessage(@PathVariable(value = "messageNo") int messageNo) {
		try {
			Message message = messageService.getMessage(messageNo);
			if (message != null) {
				return ResponseEntity.ok(message);
			} else
				return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "해당 메시지를 저장한다")
	@PostMapping("/message")
	public ResponseEntity<?> writeMessage(@RequestBody Message message) {
		try {
			Member member = memberService.getUser(message.getToId());
			int cnt = 0;
			if (member != null) {
				cnt = messageService.writeMessage(message);
			}
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "읽음 처리")
	@PutMapping("/message/update")
	public ResponseEntity<?> updateRead(@RequestBody Message message) {
		try {
			int cnt = messageService.updateRead(message);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}
}
