package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.api.selenium.SeleniumCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeetcodeApiCrawlerWithLogin implements LeetcodeJsonCrawler<User> {
    private final SeleniumCrawler seleniumCrawler;

    @Override
    public List<CrawledProblemInfo> crawlProblems(User user) {

        return seleniumCrawler.crawlApi(user)
                .toCrawledProblemInfo();
    }

    @Override
    public CrawledUserInfo crawlUserInfo(User user) {
        CrawledUserInfo crawledUserInfo = seleniumCrawler.crawlApi(user)
                .toCrawledUserInfo();

        if (user.isNotUpdated(crawledUserInfo)) {
            return CrawledUserInfo.NOT_UPDATED_USER_INFO;
        }

        return crawledUserInfo;
    }
}
