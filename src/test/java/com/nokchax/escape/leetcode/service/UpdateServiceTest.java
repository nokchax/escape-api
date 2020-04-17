package com.nokchax.escape.leetcode.service;


import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@Slf4j
class UpdateServiceTest extends ServiceLayerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private EntryService entryService;
    @MockBean
    private ProblemService problemService;
    @MockBean
    private LeetcodePageCrawler pageCrawler;
    @MockBean
    private LeetcodeApiCrawlerWithLogin apiCrawler;
    @Autowired
    private UpdateService updateService;

    @Test
    void initTest() {

    }
}