package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.exception.CrawlException;
import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateService {
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

    public List<EntryDto> updateLatestMission(UpdateCommand.UpdateArgument argument) {
        log.debug("UPDATE USER STARTED");
        List<User> users = userService.findByArgument(argument);

        List<CompletableFuture<User>> futures = users.stream()
                .map(user -> {
                    try {
                        return updateUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null; // null을 리턴해도 괜찮나?
                    }
                })
                .collect(Collectors.toList());

        log.debug("WAITING CRAWLING USERS");
        futures.stream()
                .filter(Objects::nonNull)
                .forEach(CompletableFuture::join);
        log.debug("UPDATE USER END");

        return entryService.updateEntryInLatestMission(users);
    }

    /*
        1. api 크롤해서 업데이트 (로그인 크롤)
        2. 기존 problem과 새 문제들 비교 후 업데이트 된 문제들 저장
        3. 업데이트 된 문제들 모두 리턴.
     */
    public List<ProblemDto> updateProblems() {
        User user = userService.findRandomUser();
        List<CrawledProblemInfo> crawledProblemInfos = apiCrawler.crawlProblems(user);

        return problemService.checkProblemIsNewOrUpdated(crawledProblemInfos);
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<User> updateUser(User user) {
        log.debug("{} : {}", Thread.currentThread().getName(), user.getId());

        return crawlers.stream()
                .map(crawler -> {
                    try {
                        return updateUser(user, crawler);
                    } catch (Exception e) {
                        log.error("Fail to update using [{}] / exception : [{}]", crawler.getClass().getName(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new CrawlException(user.getId()));
    }

    private CompletableFuture<User> updateUser(User user, LeetcodeCrawler<User> crawler) {
        CrawledUserInfo crawledUserInfo = crawler.crawlUserInfo(user);

        if(crawledUserInfo.isNotUpdate() || !problemService.checkSolvedProblemExist(user, crawledUserInfo)) {
            return CompletableFuture.completedFuture(user);
        }

        // 문제 업데이트를 했는데 여전히 푼 문제의 count 수가 동일하지 않다면 null을 리턴하여 이후 크롤을 시도
        return user.isNotUpdated(crawledUserInfo)
                ? CompletableFuture.completedFuture(user)
                : null;
    }
}
