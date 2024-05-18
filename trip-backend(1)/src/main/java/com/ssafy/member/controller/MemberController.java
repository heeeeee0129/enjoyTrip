package com.ssafy.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.service.BoardService;
import com.ssafy.district.dto.Sido;
import com.ssafy.district.service.DistrictServiceImpl;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.trip.service.TripService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userapi")
public class MemberController {

	private MemberService memberService;
	private DistrictServiceImpl districtService;

	public MemberController(MemberService memberService, DistrictServiceImpl districtService) {
		super();
		this.memberService = memberService;
		this.districtService = districtService;
	}

	@Operation(summary = "아이디 중복 체크")
	@GetMapping("user/checkId/{userId}")
	private ResponseEntity<?> getUser(@PathVariable(value = "userId") String userId) {
		try {
			Member member = memberService.getUser(userId);
			return ResponseEntity.ok(member);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "시/도 정보 얻어오기")
	@GetMapping("/user/sido")
	private ResponseEntity<?> getSidos() {
		try {
			List<Sido> sidos = districtService.getSidos();
			return ResponseEntity.ok(sidos);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "시/도 정보를 가져와서 회원가입 페이지로 이동?")
	@GetMapping("/user/mvjoin")
	private ResponseEntity<?> mvJoin() {
		try {
			List<Sido> sidos = districtService.getSidos();
			return ResponseEntity.ok(sidos);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "비밀번호 찾기")
	@GetMapping("/user/findPass")
	private ResponseEntity<?> findPass(@RequestParam(value = "id") String id) {
		try {
			String pwd = memberService.findPass(id);
			return ResponseEntity.ok(pwd);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/user/remove")
	public ResponseEntity<?> deleteArticle(@RequestParam(value = "id") String id, HttpSession httpSession) {
		try {
			int cnt = memberService.expire(id);
			httpSession.removeAttribute("loginUser");
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/user/logout")
	private ResponseEntity<?> logout(HttpSession httpSession) {

		try {
			httpSession.removeAttribute("loginUser");
			// httpSession.invalidate();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "로그인")
	@PostMapping("/user/login")
	private ResponseEntity<?> login(@RequestBody Member member, HttpSession httpSession) {

		try {
			Member loginMember = memberService.loginMember(member);
			if (loginMember != null) {
				httpSession.setAttribute("loginUser", loginMember);
				return ResponseEntity.ok(loginMember);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원정보수정")
	@PutMapping("/user")
	public ResponseEntity<?> modify(@RequestBody Member member) {
		try {
			int cnt = memberService.modify(member);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "회원가입")
	@PostMapping("/user")
	public ResponseEntity<?> join(@RequestBody Member member) {
		try {
			Member check = memberService.getUser(member.getId());
			int cnt = 0;
			if (check == null) {
				cnt = memberService.join(member);
			}
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
