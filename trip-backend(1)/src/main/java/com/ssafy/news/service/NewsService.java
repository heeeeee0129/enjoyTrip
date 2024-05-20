package com.ssafy.news.service;

import java.io.IOException;
import java.util.List;

import com.ssafy.news.dto.News;

public interface NewsService {
	public List<News> getNewsDatas() throws IOException ;
}
