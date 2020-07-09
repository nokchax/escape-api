package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.nokchax.escape.exception.CrawlException;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeleniumCrawler {
    private static final int MAX_TRY_COUNT = 3;

    private final SeleniumPool seleniumPool;

    public LeetcodeApiResponse crawlApi(User user) {
        return crawlApi(user, 1);
    }

    private LeetcodeApiResponse crawlApi(User user, int tryCount) {
        try {
            return seleniumPool.getSeleniumBrowser(user)
                    .crawlApi();
        } catch (CrawlException | WebDriverException e) {
            return retry(user, tryCount, e);
        }
    }

    private LeetcodeApiResponse retry(User user, int tryCount, RuntimeException e) {
        // 로그인이 안됐거나, 로그인이 풀렸다면 재시도
        if (tryCount < MAX_TRY_COUNT) {
            log.info("Crawl api retry count[{}] and user id[{}]", tryCount, user.getId());
            return crawlApi(user, tryCount + 1);
        }

        // 기존 브라우저 셧다운
        seleniumPool.removeSeleniumBrowser(user);
        throw new CrawlException("Fail to crawl retry count over " + MAX_TRY_COUNT + " : Detail [" + e.getMessage() + "]");
    }
}
