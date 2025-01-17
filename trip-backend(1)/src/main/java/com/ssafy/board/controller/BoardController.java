package com.ssafy.board.controller;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardListDto;
import com.ssafy.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ssafy.algorithm.service.Kmp;
import com.ssafy.algorithm.service.KmpImpl;
import com.ssafy.algorithm.service.SlangService;

@RestController
@RequestMapping("/boardapi")
@Tag(name = "게시판 컨트롤러", description = "게시판에 글을 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public class BoardController {

	private BoardService boardService;
	private Kmp kmp;
	private SlangService slangservice;
	private List<String> slangList;
//	private Sort sortService;

	public BoardController(BoardService boardService, SlangService slangservice) {
		super();
		this.boardService = boardService;
		this.slangservice = slangservice;
		kmp = KmpImpl.getKmp();
//		sortService = Sort.getInstance();

		try {
			slangList = this.slangservice.getList(); // 등록된 비속어 리스트 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Operation(summary = "게시판 글목록", description = "모든 게시글의 정보를 반환한다.")
	@GetMapping("/board")
	public ResponseEntity<?> listArticle(
			@RequestParam @Parameter(description = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		try {
			if(!map.get("key").isEmpty() && !map.get("word").isEmpty()) {
				map.put("pgno", "1");
			}
			BoardListDto boardListDto = boardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "{articleno}에 해당하는 게시판 글 정보를 반환한다.")
	@GetMapping("/board/{articleno}")
	public ResponseEntity<?> getArticle(@PathVariable(value = "articleno") int articleno) {
		try {
			boardService.updateHit(articleno);
			Board board = boardService.getArticle(articleno);
			if (board != null && board.getSubject().length() > 0) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

//	@Operation(summary = "등록된 모든 게시판 글 정보를 정렬기준에 따라 반환한다.")
//	@GetMapping("/board/sort/{sortby}")
//	public ResponseEntity<?> sortArticle(@PathVariable(value = "sortby") String sortby) {
//		try {
//			List<Board> boards = boardService.listArticle();
//			if (!boards.isEmpty()) {
//				if ("register".equals(sortby)) {
//					boards = sortService.sortByRegister(boards);
//				} else if ("popularity".equals(sortby)) {
//					boards = sortService.sortByPopularity(boards);
//				} else {
//					boards = sortService.sortByAlpha(boards);
//				}
//				return ResponseEntity.ok(boards);
//			} else {
//				return ResponseEntity.noContent().build();
//			}
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}

	@Operation(summary = "게시판의 글을 저장한다.")
	@PostMapping("/board")
	public ResponseEntity<?> writeArticle(@RequestBody Board board) {
		try {
			boolean flag = false;
			int cnt = 0;
			for (String str : slangList) {
				boolean subjectCheck = kmp.check(board.getSubject(), str);
				boolean contentCheck = kmp.check(board.getContent(), str);
				if (subjectCheck || contentCheck) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				cnt = boardService.writeArticle(board);
				return ResponseEntity.ok(cnt);
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시판의 글을 수정한다.")
	@PutMapping("/board")
	public ResponseEntity<?> modifyArticle(@RequestBody Board board) {
		try {
			boolean flag = false;
			int cnt = 0;
			for (String str : slangList) {
				boolean subjectCheck = kmp.check(board.getSubject(), str);
				boolean contentCheck = kmp.check(board.getContent(), str);
				if (subjectCheck || contentCheck) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				cnt = boardService.modifyArticle(board);
				return ResponseEntity.ok(cnt);
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "게시판의 글을 삭제한다.")
	@DeleteMapping("/board/{articleno}")
	public ResponseEntity<?> deleteArticle(@PathVariable(value = "articleno") int articleno) {
		try {
			int cnt = boardService.deleteArticle(articleno);
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
