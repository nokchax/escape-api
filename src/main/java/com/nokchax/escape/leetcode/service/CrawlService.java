package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlService {
    private final UserService userService;
    private final EntryService entryService;
    private final ProblemService problemService;
    private final LeetcodePageCrawler pageCrawler;
    private final LeetcodeApiCrawlerWithLogin apiCrawler;
    private List<LeetcodeCrawler<User>> crawlers;

    @PostConstruct
    void init() {
        List<LeetcodeCrawler<User>> leetcodeCrawlers = new ArrayList<>();

        leetcodeCrawlers.add(pageCrawler);
        leetcodeCrawlers.add(apiCrawler);

        crawlers = leetcodeCrawlers;
    }

    // TODO: 2020-05-09 User entity를 사용하지 말고 DTO 를 사용해서 처리해야지 Non thread safe한 Transaction manager의 문제를 해결할 수 있을듯 적어도 크롤시점에서는 사용하지 말자
}
