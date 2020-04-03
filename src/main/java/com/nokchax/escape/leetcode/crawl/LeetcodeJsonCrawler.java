package com.nokchax.escape.leetcode.crawl;

import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;

import java.util.List;

public interface LeetcodeJsonCrawler<T> extends LeetcodeCrawler<T> {
    List<CrawledProblemInfo> crawlProblems(T paramType) throws Exception;
}
