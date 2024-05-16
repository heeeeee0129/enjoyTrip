package com.ssafy.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.notice.dto.Notice;
import com.ssafy.notice.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/noticeapi")
public class NoticeController {
	private NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}

	@Operation(summary = "등록된 모든 공지사항 정보를 가져온다.")
	@GetMapping("/notice")
	public ResponseEntity<?> listArticle() {
		try {
			List<Notice> notices = noticeService.listArticle();
			if (!notices.isEmpty()) {
				return ResponseEntity.ok(notices);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "{articleno}에 해당하는 공지사항 글 정보를 반환한다.")
	@GetMapping("/notice/{articleno}")
	public ResponseEntity<?> getArticle(@PathVariable(value = "articleno") int articleno) {
		try {
			noticeService.updateHit(articleno);
			Notice notice = noticeService.getArticle(articleno);
			if (notice != null && notice.getSubject().length() > 0) {
				return ResponseEntity.ok(notice);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "공지사항의 글을 저장한다.")
	@PostMapping("/notice")
	public ResponseEntity<?> writeArticle(@RequestBody Notice notice) {
		try {
			int cnt = noticeService.writeArticle(notice);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "공지사항의 글을 수정한다.")
	@PutMapping("/notice")
	public ResponseEntity<?> modifyArticle(@RequestBody Notice notice) {
		try {
			int cnt = noticeService.modifyArticle(notice);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "게시판의 글을 삭제한다.")
	@DeleteMapping("/notice/{articleno}")
	public ResponseEntity<?> deleteArticle(@PathVariable(value = "articleno") int articleno) {
		try {
			int cnt = noticeService.deleteArticle(articleno);
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
