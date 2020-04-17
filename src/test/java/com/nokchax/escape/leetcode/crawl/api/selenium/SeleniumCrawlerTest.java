package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.nokchax.escape.exception.CrawlException;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SeleniumCrawlerTest {
    @Mock
    private final SeleniumPool seleniumPool = mock(SeleniumPool.class);
    @Mock
    private final SeleniumBrowser seleniumBrowser = mock(SeleniumBrowser.class);
    private final SeleniumCrawler seleniumCrawler = new SeleniumCrawler(seleniumPool);

    private final User user = new User("nokchax", "123", "greentea");

    @Test
    @DisplayName("올바르게 크롤링을 완료했다고 가정했을 때")
    void crawlTest() {
        given(seleniumPool.getSeleniumBrowser(user)).willReturn(seleniumBrowser);
        given(seleniumBrowser.crawlApi()).willReturn(
                LeetcodeApiResponse.builder()
                        .userId("nokchax")
                        .solvedProblemCount(0)
                        .totalProblemCount(1000L)
                        .problemInfos(Collections.emptyList())
                        .build()
        );

        LeetcodeApiResponse apiResponse = seleniumCrawler.crawlApi(user);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getSolvedProblemCount()).isEqualTo(0);
        assertThat(apiResponse.getTotalProblemCount()).isEqualTo(1000L);
        assertThat(apiResponse.getProblemInfos()).isNotNull();

        System.out.println(apiResponse);
    }

    @Test
    @DisplayName("크롤시 뭔지 모를 에러가 났을때 3번 까지 재시도 이후 익셉션 발생")
    void crawlTest_Exception() {
        given(seleniumBrowser.crawlApi()).willThrow(CrawlException.class);
        given(seleniumPool.getSeleniumBrowser(any())).willReturn(seleniumBrowser);

        assertThatThrownBy(() -> seleniumCrawler.crawlApi(user)).isInstanceOf(CrawlException.class);

        verify(seleniumPool, times(3)).getSeleniumBrowser(any());
        verify(seleniumBrowser, times(3)).crawlApi();
    }
}