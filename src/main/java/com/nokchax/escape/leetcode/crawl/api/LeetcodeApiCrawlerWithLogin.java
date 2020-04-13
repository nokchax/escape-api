package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.exception.CrawlException;
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
    public List<CrawledProblemInfo> crawlProblems(User user) {
        LeetcodeApiResponse apiResponse = getLeetcodeApiResponse(user);

        return apiResponse.toCrawledProblemInfo();
    }

    @Override
    public CrawledUserInfo crawlUserInfo(User user) {
        CrawledUserInfo crawledUserInfo = doCrawl(user);

        if(user.isNotUpdated(crawledUserInfo)) {
            return CrawledUserInfo.NOT_UPDATED_USER_INFO;
        }

        return crawledUserInfo;
    }

    private CrawledUserInfo doCrawl(User user) {
        log.debug("Start selenium crawl [{}]", user.getId());

        LeetcodeApiResponse apiResponse = getLeetcodeApiResponse(user);

        return apiResponse.toCrawledUserInfo();
    }

    private LeetcodeApiResponse getLeetcodeApiResponse(User user) {
        try {
            return Selenium.openBrowser(userAgentQueue)
                    .toLoginPage()
                    .doLogin(user)
                    .doCrawl();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CrawlException();
        }
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
