package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.api.selenium.Selenium;
import com.nokchax.escape.leetcode.crawl.api.selenium.UserAgentQueue;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeetcodeApiCrawlerWithLogin implements LeetcodeJsonCrawler<User> {
    private final UserAgentQueue userAgentQueue;

    @Override
    public void crawlProblems(User user) {

    }

    @Override
    public CrawledUserInfo crawlUserInfo(User user) throws Exception {
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
