package com.ssafy.friend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.friend.dto.Friend;
import com.ssafy.friend.service.FriendService;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/friendapi")
public class FriendController {

	private FriendService friendService;
	private MemberService memberService;

	public FriendController(FriendService friendService, MemberService memberService) {
		super();
		this.friendService = friendService;
		this.memberService = memberService;
	}

	@Operation(summary = "등록된 친구를 가져온다.")
	@GetMapping("/friend/{userId}")
	public ResponseEntity<?> listFriend(@PathVariable(value = "userId") String userId) {
		try {
			List<Friend> friends = friendService.listFriend(userId);
			if (!friends.isEmpty()) {
				return ResponseEntity.ok(friends);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "친구 추가")
	@PostMapping("/friend")
	public ResponseEntity<?> addFriend(@RequestBody Friend friend) {
		try {
			Member check = memberService.getUser(friend.getFriendId());
			int ck = friendService.getFriend(friend);
			int cnt = -1;
			if (check != null && ck == 0) {
				cnt = friendService.addFriend(friend);
			}
			//이미 추가되어있는 경우 
			else if(check != null && ck > 0) {
				cnt = 0;
			}
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "친구 삭제")
	@PostMapping("/friend/delete")
	public ResponseEntity<?> deleteFriend(@RequestBody Friend friend) {
		try {
			int cnt = friendService.deleteFriend(friend);
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
