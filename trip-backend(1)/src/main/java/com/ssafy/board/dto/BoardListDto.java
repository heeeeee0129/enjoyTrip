package com.ssafy.board.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "BoardListDto : 게시글 목록 & 페이지 정보", description = "게시글 목록과 현재 페이지와 전체 페이지 정보를 나타낸다.")
public class BoardListDto {

	@Schema(description = "글목록")
	private List<Board> articles;
	@Schema(description = "현재 페이지번호")
	private int currentPage;
	@Schema(description = "전체 페이지 수")
	private int totalPageCount;

	public List<Board> getArticles() {
		return articles;
	}

	public void setArticles(List<Board> articles) {
		this.articles = articles;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	@Override
	public String toString() {
		return "BoardListDto [articles=" + articles + ", currentPage=" + currentPage + ", totalPageCount="
				+ totalPageCount + "]";
	}

}
