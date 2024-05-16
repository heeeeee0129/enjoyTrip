package com.ssafy.comment.controller;

import java.sql.SQLException;
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


import com.ssafy.algorithm.service.Kmp;
import com.ssafy.algorithm.service.KmpImpl;
import com.ssafy.algorithm.service.SlangService;
import com.ssafy.comment.dto.Comment;
import com.ssafy.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/commentapi")
public class CommentController {
	private CommentService commentService;
	private Kmp kmp;
	private SlangService slangservice;
	private List<String> slangList;

	public CommentController(CommentService commentService, SlangService slangservice) {
		super();
		this.commentService = commentService;
		this.slangservice = slangservice;
		kmp = KmpImpl.getKmp();

		try {
			slangList = this.slangservice.getList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Operation(summary = "해당 게시글의 총 댓글수를 가져온다.")
	@GetMapping("/comment/count/{articleNo}")
	public ResponseEntity<?> getCountComment(@PathVariable(value = "articleNo") int articleNo) {
		try {
			int cnt = commentService.getCountComment(articleNo);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "해당 게시글의 댓글목록을 가져온다.")
	@GetMapping("/comment/list/{articleNo}")
	public ResponseEntity<?> listComment(@PathVariable(value = "articleNo") int articleNo) {
		try {
			List<Comment> listComment = commentService.listComment(articleNo);
			if (!listComment.isEmpty()) {
				return ResponseEntity.ok(listComment);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "해당 댓글의 답글목록을 가져온다.")
	@GetMapping("/comment/listRe/{replyNo}")
	public ResponseEntity<?> listReComment(@PathVariable(value = "replyNo") int replyNo) {
		try {
			List<Comment> listComment = commentService.listReComment(replyNo);
			if (!listComment.isEmpty()) {
				return ResponseEntity.ok(listComment);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "해당 댓글을 가져온다.")
	@GetMapping("/comment/{replyNo}")
	public ResponseEntity<?> getComment(@PathVariable(value = "replyNo") int replyNo) {
		try {
			Comment comment = commentService.getComment(replyNo);
			if (comment != null) {
				return ResponseEntity.ok(comment);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "해당 게시글의 댓글을 저장한다.")
	@PostMapping("/comment")
	public ResponseEntity<?> writeComment(@RequestBody Comment comment) {
		try {
			boolean flag = false;
			int cnt = 0;
			for(String str : slangList) {
				flag = kmp.check(comment.getContent(), str);
				if(flag) break;
			}
			
			if(!flag) {
				cnt = commentService.writeComment(comment);
				return ResponseEntity.ok(cnt);
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "해당 댓글을 수정한다.")
	@PutMapping("/comment")
	public ResponseEntity<?> modifyComment(@RequestBody Comment comment) {
		try {
			boolean flag = false;
			int cnt = 0;
			for(String str : slangList) {
				flag = kmp.check(comment.getContent(), str);
				if(flag) break;
			}
			
			if(!flag) {
				cnt = commentService.modifyComment(comment);
				return ResponseEntity.ok(cnt);
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary ="해당 댓글을 삭제한다.")
	@DeleteMapping("/comment/{replyNo}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "replyNo") int replyNo) {
		try {
			int cnt = commentService.deleteComment(replyNo);
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
