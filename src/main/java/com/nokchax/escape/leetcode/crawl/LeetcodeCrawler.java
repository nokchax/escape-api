package com.nokchax.escape.leetcode.crawl;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;

import java.util.Optional;

/*
    basic - crawl without login
    selenium - crawl with login
    json - crawl with json file
 */
public interface LeetcodeCrawler<T> {
    Optional<CrawledUserInfo> crawlUserInfo(T paramType);
}
