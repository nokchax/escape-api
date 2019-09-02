package com.zum.escape.api.thirdPartyAdapter.leetcode.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeetCodeService {
    private static final String LEET_CODE_PROBLEM_API_URL = "https://leetcode.com/api/problems/all";
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
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        return headers;
    }
}
