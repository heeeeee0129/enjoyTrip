package com.ssafy.news.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.ssafy.news.dto.News;


@Service
public class NewsServiceImpl implements NewsService{
	
	 private final static String News_URL = "https://www.joongang.co.kr/travel/domestic";
	
//    @PostConstruct
    public List<News> getNewsDatas() throws IOException {
        List<News> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();

        Elements contents = document.select("section div ul.card_group li");

        for (Element content : contents) {
            News news = new News(content.select("figure a img").attr("abs:src"), content.select("div h2 a").text(), content.select("div h2 a").attr("abs:href"));
            newsList.add(news);
        }

        return newsList;
    }
}
