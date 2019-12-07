package com.zum.escape.api.thirdPartyAdapter.leetcode.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.Submission;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.util.DateStringToLocalDateTimeConverter;
import com.zum.escape.api.util.LeetcodeUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeetCodeService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";

    public ProblemResponse getProblems(User user) {
        ProblemResponse problemResponse = null;

        try {
            /// TODO: 2019-12-07 do login

            // TODO: 2019-12-07 get response
            Response response = null;
            String responseData = response.body().string();

            log.info("update problems api response : {}", response);

            //problemResponse = okHttpHelper.fromJson(responseData, ProblemResponse.class);
        } catch (HttpClientErrorException e) {
            log.error("Fail to get problems list from leetcode : {}", e.getMessage());
        } catch (IOException e) {
            log.error("IOException occurred : {}", e.getMessage());
        }

        return problemResponse;
    }

    public CrawledUserInfo findUser(String userId) throws IOException {
        Connection connection = Jsoup.connect(LeetcodeUrl.USER_URL + userId);
        connection.header(HttpHeaders.USER_AGENT, USER_AGENT);

        Document document = connection.get();

        return extractUser(document, userId);
    }

    private CrawledUserInfo extractUser(Document document, String userId) {
        Elements problems = document.getElementsByAttributeValueStarting("href", "/problems");
        Elements solvedQuestionElement = document.getElementsByClass("fa-question").parents().get(0).getElementsByClass("progress-bar-success");

        int solvedQuestionCount = solvedQuestionElement.isEmpty() ? 0 : extractSolvedQuestionCount(solvedQuestionElement.get(0).text());
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

                solvedProblems.add(new Submission(problemName, DateStringToLocalDateTimeConverter.convert(time)));
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
