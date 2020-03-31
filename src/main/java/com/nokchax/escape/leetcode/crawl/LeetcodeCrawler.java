package com.nokchax.escape.leetcode.crawl;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;

/*
    basic - crawl without login
    selenium - crawl with login
    json - crawl with json file
 */
public interface LeetcodeCrawler<T> {
    CrawledUserInfo crawlUserInfo(T paramType);
}
