package com.zum.escape.api.thirdPartyAdapter.leetcode.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.Submission;
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
import java.time.LocalDateTime;
import java.util.HashSet;
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

        return extractUser(document, userId);
    }

    private CrawledUserInfo extractUser(Document document, String userId) {
        Elements problems = document.getElementsByAttributeValueStarting("href", "/problems");
        Elements solvedQuestionElement = document.getElementsByClass("fa-question").parents().get(0).getElementsByClass("progress-bar-success");

        int solvedQuestionCount = extractSolvedQuestionCount(solvedQuestionElement.get(1).text());
        Set<Submission> solvedProblems = extractSolvedProblems(problems);

        return CrawledUserInfo.builder()
                .userId(userId)
                .solvedQuestionCount(solvedQuestionCount)
                .solvedProblems(solvedProblems)
                .build();
    }

    private Set<Submission> extractSolvedProblems(Elements problems) {
        Set<Submission> solvedProblems = new HashSet<>();

        for(Element problem : problems) {
            String accepted = problem.getElementsByTag("span").get(0).text().trim();
            if("Accepted".equalsIgnoreCase(accepted)) {
                String problemName = problem.getElementsByTag("b").text().trim();
                String time = problem.getElementsByClass("text-muted").get(0).text();

                solvedProblems.add(new Submission(problemName, extractDateTime(time)));
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

    private LocalDateTime extractDateTime(String time) {
        String[] times = time.split(",");
        LocalDateTime now = LocalDateTime.now();

        for(String t : times) {
            t = t.replace(((char)160),' ');
            String[] inputs = t.trim().split("\\s+");

            int num = Integer.parseInt(inputs[0]);
            if(inputs.length < 2) {
                log.error("Fail to parse time : {}", t);
                return now;
            }

            switch(inputs[1].trim()) {
                case "minute":
                case "minutes":
                    now = now.minusMinutes(num);
                    break;
                case "hour":
                case "hours":
                    now = now.minusHours(num);
                    break;

                case "week":
                case "weeks":
                    now = now.minusWeeks(num);
                    break;

                case "day":
                case "days":
                    now = now.minusDays(num);
                    break;

                case "month":
                case "months":
                    now = now.minusMonths(num);
                    break;

                default:
                    log.error("Fail to parse time : {}", t);
            }
        }

        return now;
    }
}
