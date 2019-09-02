package com.zum.escape.api.thirdPartyAdapter.leetcode.service;


import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class LeetCodeServiceTest {
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
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        return headers;
    }
}