package com.nokchax.escape.leetcode.crawl.api;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LeetcodeApiCrawler implements LeetcodeJsonCrawler<User> {

    @Override
    public List<CrawledProblemInfo> crawlProblems(User user) {

        return null;
    }

    @Override
    public CrawledUserInfo crawlUserInfo(User paramType) {
        return null;
    }
}
