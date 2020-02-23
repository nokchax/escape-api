package com.zum.escape.api.thirdPartyAdapter.leetcode.service;


import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.util.LeetcodeUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

//@Slf4j
public class LeetCodeServiceTest {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
    private RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    public void init() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);
    }

    @Test
    public void getProblemsTest() {
        try {
            HttpEntity<String> headers = new HttpEntity<>(makeHttpHeaders());
            ResponseEntity<ProblemResponse> problemResponse = restTemplate.exchange(LeetcodeUrl.PROBLEM_API_URL, HttpMethod.GET, headers, ProblemResponse.class);

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
/*
        Connection connection = Jsoup.connect(LeetcodeUrl.USER_URL + "nokchax");
        connection.header(HttpHeaders.USER_AGENT, USER_AGENT);


        Document document = connection.get();
        Elements elements = document.getElementsByClass("fa-question").parents().get(0).getElementsByClass("progress-bar-success");
        log.info("=============================================================");
        log.info(elements.get(0).text());
        log.info("=============================================================");
        Elements href = document.getElementsByAttributeValueStarting("href", "/problems");
        log.info("=============================================================");
        log.info("{}", href);
        log.info("=============================================================");
        for(Element e : href) {
            String problem = e.getElementsByTag("b").text();
            String accepted= e.getElementsByTag("span").get(0).text();
            String time = e.getElementsByClass("text-muted").get(0).text();
            log.info(problem + " : " + accepted + " : " + time);
        }
*/

    }
    @Test
    public void LoginTest() throws IOException {
/*
        User user = new User("test","test","test");
        // TODO: 2019-12-07 login test
        log.info("{}", new UserProblemCrawlService().getUserProblems(user).toUserProblemList(user));
*/
    }
}