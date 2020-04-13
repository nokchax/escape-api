package com.nokchax.escape.leetcode.crawl.file;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LeetcodeJsonFileCrawler implements LeetcodeJsonCrawler<String> {

    @Override
    public List<CrawledProblemInfo> crawlProblems(String json) {

        return null;
    }

    @Override
    public CrawledUserInfo crawlUserInfo(String paramType) {
        return null;
    }
}
