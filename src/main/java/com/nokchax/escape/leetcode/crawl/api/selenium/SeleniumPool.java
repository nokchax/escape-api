package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.CrawlException;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SeleniumPool {
    private static final int MAX_TRY_COUNT = 3;
    private final AppProperties properties;
    private final ObjectMapper objectMapper;
    private final UserAgentQueue userAgentQueue;
    private final Map<String, SeleniumBrowser> seleniumPool = new HashMap<>(); // key : user's id

    public LeetcodeApiResponse crawlApi(User user) {
        return crawlApi(user, 0);
    }

    private LeetcodeApiResponse crawlApi(User user, int tryCount) {
        try {
            return getSeleniumBrowser(user).crawlApi();
        } catch (CrawlException | WebDriverException e) {
            return retry(user, tryCount, e);
        }
    }

    private LeetcodeApiResponse retry(User user, int tryCount, RuntimeException e) {
        // 로그인이 안됐거나, 로그인이 풀렸다면 재시도
        if(tryCount < MAX_TRY_COUNT) {
            return crawlApi(user, tryCount + 1);
        }

        // 기존 브라우저 셧다운
        removeSeleniumBrowser(user);
        throw new CrawlException("Fail to crawl retry count over 3 : Detail [" + e.getMessage() + "]");
    }

    private SeleniumBrowser getSeleniumBrowser(User user) {
        if(!seleniumPool.containsKey(user.getId())) {
            seleniumPool.put(user.getId(), SeleniumBrowser.of(user, userAgentQueue, objectMapper));
        }

        return seleniumPool.get(user.getId());
    }

    private void removeSeleniumBrowser(User user) {
        seleniumPool.get(user.getId())
                .close();

        seleniumPool.remove(user.getId());
    }

    @PostConstruct
    private void init() {
        System.setProperty("webdriver.chrome.driver", properties.getSelenium().getDriverPath());
    }
}
