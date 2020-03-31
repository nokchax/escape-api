package com.nokchax.escape.leetcode.crawl;

public interface LeetcodeJsonCrawler<T> extends LeetcodeCrawler<T> {
    void crawlProblems(T paramType);
}
