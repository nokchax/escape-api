package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class LeetcodeApiCrawlerWithLogin implements LeetcodeJsonCrawler<User> {

    @Override
    public void crawlProblems(User user) {

    }

    @Override
    public CrawledUserInfo crawlUserInfo(User paramType) {
        return null;
    }
}
