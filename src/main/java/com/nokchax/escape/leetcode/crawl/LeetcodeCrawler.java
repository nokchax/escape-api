package com.nokchax.escape.leetcode.crawl;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;

import java.util.Optional;

public interface LeetcodeCrawler<T> {
    CrawledUserInfo crawlUserInfo(T paramType);
}
