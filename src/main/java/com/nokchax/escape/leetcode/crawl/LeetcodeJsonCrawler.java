package com.nokchax.escape.leetcode.crawl;

import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;

public interface LeetcodeJsonCrawler<T> extends LeetcodeCrawler<T> {
    void crawlProblems(T paramType);
}
