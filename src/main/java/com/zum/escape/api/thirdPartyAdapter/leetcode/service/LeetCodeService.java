package com.zum.escape.api.thirdPartyAdapter.leetcode.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeetCodeService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
    private static final String LEET_CODE_PROBLEM_API_URL = "https://leetcode.com/api/problems/all";
    private static final String LEET_CODE_URL = "https://leetcode.com/";
    private final RestTemplate restTemplate;

    public ProblemResponse getProblems() {
        ResponseEntity<ProblemResponse> problemResponse = null;

        try {
            problemResponse = restTemplate.exchange(
                    LEET_CODE_PROBLEM_API_URL,
                    HttpMethod.GET,
                    new HttpEntity<>(makeHttpHeaders()),
                    ProblemResponse.class
            );
        } catch (HttpClientErrorException e) {
            log.error("Fail to get problems list from leetcode : {}", e.getMessage());
        }

        return problemResponse.getBody();
    }

    private HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT);
        return headers;
    }

    public CrawledUserInfo findUser(String userId) throws IOException {
        Connection connection = Jsoup.connect(LEET_CODE_URL + userId);
        connection.header(HttpHeaders.USER_AGENT, USER_AGENT);

        Document document = connection.get();

        return extractUser(document);
    }

    private CrawledUserInfo extractUser(Document document) {
        Elements problems = document.getElementsByAttributeValueStarting("href", "/problems");
        Elements solvedQuestionElement = document.getElementsByClass("progress-bar-success");

        int solvedQuestionCount = extractSolvedQuestionCount(solvedQuestionElement.get(1).text());
        Set<String> solvedProblems = extractSolvedProblems(problems);

        return CrawledUserInfo.builder()
                .solvedQuestionCount(solvedQuestionCount)
                .solvedProblems(solvedProblems)
                .build();
    }

    private Set<String> extractSolvedProblems(Elements problems) {
        Set<String> solvedProblems = Collections.EMPTY_SET;

        for(Element problem : problems) {
            String accepted = problem.getElementsByTag("span").get(0).text();
            if("Accepted".equalsIgnoreCase(accepted)) {
                String problemName = problem.getElementsByTag("b").text();
                solvedProblems.add(problemName);
            }
        }

        return solvedProblems;
    }

    private int extractSolvedQuestionCount(String text) {
        if(!StringUtils.hasText(text))
            return 0;

        String[] inputs = text.split("/");

        return Integer.parseInt(inputs[0].trim());
    }
}
