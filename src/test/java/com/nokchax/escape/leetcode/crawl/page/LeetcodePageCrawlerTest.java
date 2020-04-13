package com.nokchax.escape.leetcode.crawl.page;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.nokchax.escape.leetcode.crawl.page.util.ExtractUtilTest.PAGE_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
class LeetcodePageCrawlerTest extends ServiceLayerTest {
    @Autowired
    private LeetcodePageCrawler leetcodePageCrawler;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("유저가 추가적인 문제를 풀지 않았을 때")
    void crawlTest_WhenUserNotSolvedMoreProblems() {
        when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(PAGE_RESPONSE, HttpStatus.OK));

        User user = userRepository.findById("nokchax1")
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setSolvedProblemCount(125);

        CrawledUserInfo crawledUserInfo = leetcodePageCrawler.crawlUserInfo(user);

        assertThat(crawledUserInfo).isNotNull();
        assertThat(crawledUserInfo.isNotUpdate()).isTrue();
        assertThat(crawledUserInfo.getSolvedProblemCount()).isEqualTo(0);
        assertThat(crawledUserInfo.getSolvedProblems().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("유저가 추가적인 문제를 풀었을 때")
    void crawlTest_WhenUserSolvedMoreProblems() {
        when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(PAGE_RESPONSE, HttpStatus.OK));

        User user = userRepository.findById("nokchax1")
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setSolvedProblemCount(100);

        CrawledUserInfo crawledUserInfo = leetcodePageCrawler.crawlUserInfo(user);

        assertThat(crawledUserInfo).isNotNull();
        assertThat(crawledUserInfo.isNotUpdate()).isFalse();
        assertThat(crawledUserInfo.getSolvedProblemCount()).isEqualTo(125);
        assertThat(crawledUserInfo.getSolvedProblems().size()).isEqualTo(12);

        showResult();
        System.out.println(crawledUserInfo);
        crawledUserInfo.getSolvedProblems()
                .forEach(System.out::println);
    }

}