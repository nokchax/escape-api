package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.commands.UpdateCommand;
import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.user.domain.User;
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

    public void updateUser(UpdateCommand.UpdateArgument updateArgument) {
        // update argument로 사용자 dto 받아오기 필요한건 id, 푼 문제수
        // 병렬 스트림으로 각 사용자의 정보 크롤하기
        // 크롤한 정보로 업데이트가 있는지 검사 후 푼 문제의 id만 리턴?
        // db업데이트
    }
}
