package com.ssafy.comment.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Operation(summary = "해당 게시글의 댓글을 저장한다.")
	@GetMapping("/comment/{articleNo}")
	public ResponseEntity<?> listComment(@PathVariable(value = "articleno") int articleno) {
		try {
			List<Comment> listComment = commentService.listComment(articleno);
			if (!listComment.isEmpty()) {
				return ResponseEntity.ok(listComment);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}

}
