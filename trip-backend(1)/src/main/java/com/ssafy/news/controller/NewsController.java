package com.ssafy.news.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.news.dto.News;
import com.ssafy.news.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/newsapi")
public class NewsController {

	private NewsService newsService;

	public NewsController(NewsService newsService) {
		super();
		this.newsService = newsService;
	}
	
	@Operation(summary = "뉴스 크롤링")
	@GetMapping("/news")
	public ResponseEntity<?> listArticle() {
		try {
			List<News> newsList = newsService.getNewsDatas();
			if (!newsList.isEmpty()) {
				return ResponseEntity.ok(newsList);
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
