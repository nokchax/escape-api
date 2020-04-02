package com.nokchax.escape.leetcode.crawl.page;

import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.util.ExtractUtil;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.nokchax.escape.leetcode.crawl.Leetcode.LEETCODE_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeetcodePageCrawler implements LeetcodeCrawler<User> {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
    private static final HttpEntity<String> HEADERS;

    private final RestTemplate restTemplate;

    @Override
    public CrawledUserInfo crawlUserInfo(User user) {
        Document document = crawlUserPage(user);
        CrawledUserInfo crawledUserInfo = parseDocument(document, user);

        if(user.isUpdated(crawledUserInfo)) {
            return null;
        }

        return crawledUserInfo;
    }

    private Document crawlUserPage(User user) {
        ResponseEntity<String> responseHtml = restTemplate.exchange(LEETCODE_URL + user.getId(), HttpMethod.GET, HEADERS, String.class);

        return Jsoup.parse(responseHtml.toString(), user.getId());
    }

    private CrawledUserInfo parseDocument(Document document, User user) {

        return CrawledUserInfo.builder()
                .userId(user.getId())
                .solvedProblemCount(ExtractUtil.extractSolvedProblemCount(document))
                .solvedProblems(ExtractUtil.extractSolvedProblems(document))
                .build();
    }

    static {
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.USER_AGENT, USER_AGENT);

        HEADERS = new HttpEntity<>(header);
    }
}
