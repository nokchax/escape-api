package com.zum.escape.api.thirdPartyAdapter.leetcode.service;


import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

public class LeetCodeServiceTest {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
    private RestTemplate restTemplate = new RestTemplate();

    @Before
    public void init() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);
    }

    @Test
    public void getProblemsTest() {
        try {
            HttpEntity<String> headers = new HttpEntity<>(makeHttpHeaders());
            ResponseEntity<ProblemResponse> problemResponse = restTemplate.exchange("https://leetcode.com/api/problems/all", HttpMethod.GET, headers, ProblemResponse.class);

            System.out.println("StatusCode : " + problemResponse.getStatusCode());
            System.out.println("Headers : " + problemResponse.getHeaders());
            System.out.println("Body : " + problemResponse.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            System.out.println(e.getMessage());
        }
    }

    private HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT);
        return headers;
    }


    @Test
    public void jsoupTest() throws IOException {
        Connection connection = Jsoup.connect("https://leetcode.com/" + "nokchax");
        connection.header(HttpHeaders.USER_AGENT, USER_AGENT);


        Document document = connection.get();
        System.out.println(document);

        Elements elements = document.getElementsByClass("progress-bar-success");
        Elements elements1 = document.getElementsByTag("a");
        System.out.println("=============================================================");
        System.out.println(elements.get(1).text());
        System.out.println("=============================================================");
        System.out.println(elements1);
        Elements href = document.getElementsByAttributeValueStarting("href", "/problems");
        System.out.println("=============================================================");
        System.out.println(href);
        System.out.println("=============================================================");
        for(Element e : href) {
            String problemr1 = e.attr("href");
            String problem = e.getElementsByTag("b").text();
            String accepted= e.getElementsByTag("span").get(0).text();
            System.out.println(problem + " : " + accepted);
        }

    }
}