package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.api.selenium.Selenium;
import com.nokchax.escape.leetcode.crawl.api.selenium.UserAgentQueue;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeetcodeApiCrawlerWithLogin implements LeetcodeJsonCrawler<User> {
    private final UserAgentQueue userAgentQueue;

    @Override
    public List<CrawledProblemInfo> crawlProblems(User user) throws Exception {
        LeetcodeApiResponse apiResponse = Selenium.openBrowser(userAgentQueue)
                .toLoginPage()
                .doLogin(user)
                .doCrawl();

        return apiResponse.toCrawledProblemInfo();
    }

    @Override
    public Optional<CrawledUserInfo> crawlUserInfo(User user) throws Exception {
        CrawledUserInfo crawledUserInfo = doCrawl(user);

        if(user.isNotUpdated(crawledUserInfo)) {
            return Optional.empty();
        }

        return Optional.of(crawledUserInfo);
    }

    private CrawledUserInfo doCrawl(User user) throws Exception {
        // this pattern is not good.. when process order is important
        log.debug("Start selenium crawl [{}]", user.getId());

        LeetcodeApiResponse apiResponse = Selenium.openBrowser(userAgentQueue)
                .toLoginPage()
                .doLogin(user)
                .doCrawl();

        return apiResponse.toCrawledUserInfo();
    }

    // TODO: 2020-04-02 hardcoding...
    @PostConstruct
    private void init() {
        String property = System.getProperty("os.name");
        String driverPath = "/data/etc/webdriver/chromedriver";

        if(property.startsWith("Mac")) {
            driverPath = "/Users/nokchax" + driverPath;
        }

        System.setProperty("webdriver.chrome.driver", driverPath);
    }
}
