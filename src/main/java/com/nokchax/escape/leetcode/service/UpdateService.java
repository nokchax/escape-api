package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.commands.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.exception.CrawlException;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.leetcode.crawl.LeetcodeCrawler;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
    private final UserRepository userRepository;
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

    public String fixUserProblem(String userId) {
        //user 찾아오기
        User user = userRepository.findOneByUserId(userId)
                .orElseThrow(() -> {throw new UserNotFoundException(userId);});

        // apiCrawler로 유저가 푼 문제 모두 가져오기
        CrawledUserInfo crawledUserInfo = apiCrawler.crawlUserInfo(user);

        // 푼 문제중 저장되지 않은 문제 추리기
        // 저장 되어있는데 풀리 않은 문제 찾아 삭제하기

        // 저장되지 않은 문제 저장하기
        problemService.fixSolvedProblems(crawledUserInfo);

        return "";
    }

    // TODO: 2020-05-05 transaction manager is not thread safe so detach crawl and update logic
    @Transactional
    public void updateLatestMission(UpdateCommand.UpdateArgument argument) {
        log.debug("UPDATE USER STARTED");
        List<User> users = userService.findByArgument(argument);

        List<CompletableFuture<User>> futures = users.parallelStream()
                .map(user -> {
                    try {
                        return updateUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        log.debug("WAITING CRAWLING USERS");
        futures.stream()
                .filter(Objects::nonNull)
                .forEach(CompletableFuture::join);
        log.debug("UPDATE USER END");
    }

    public List<EntryDto> updateLatestMissionAndReturnEntry(UpdateCommand.UpdateArgument argument) {
        updateLatestMission(argument);

        return returnLatestEntry(argument);
    }

    @Transactional
    public List<EntryDto> returnLatestEntry(UpdateCommand.UpdateArgument argument) {
        List<User> users = userService.findByArgument(argument);

        return entryService.updateEntryInLatestMission(users);
    }

    /*
        1. api 크롤해서 업데이트 (로그인 크롤)
        2. 기존 problem과 새 문제들 비교 후 업데이트 된 문제들 저장
        3. 업데이트 된 문제들 모두 리턴.
     */
    @Transactional
    public List<ProblemDto> updateProblems() {
        User user = userService.findRandomUser();
        List<CrawledProblemInfo> crawledProblemInfos = apiCrawler.crawlProblems(user);

        return problemService.checkProblemIsNewOrUpdated(crawledProblemInfos);
    }

    @Async("threadPoolExecutor")
    @Transactional
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

    @Transactional
    public CompletableFuture<User> updateUser(User user, LeetcodeCrawler<User> crawler) {
        CrawledUserInfo crawledUserInfo = crawler.crawlUserInfo(user);

        if (crawledUserInfo.isNotUpdate() || !problemService.checkSolvedProblemExist(user, crawledUserInfo)) {
            return CompletableFuture.completedFuture(user);
        }

        // 문제 업데이트를 했는데 여전히 푼 문제의 count 수가 동일하지 않다면 null을 리턴하여 이후 크롤을 시도
        return user.isNotUpdated(crawledUserInfo)
                ? CompletableFuture.completedFuture(user)
                : null;
    }

    public String viewUserProblem(UpdateCommand.UpdateArgument extractArgument) {
        List<User> users = userService.findByArgument(extractArgument);

        StringBuilder sb = new StringBuilder();

        users.forEach(user -> {
            CrawledUserInfo crawledUserInfo = pageCrawler.crawlUserInfo(user);
            sb.append(crawledUserInfo.toString());
        });

        return sb.toString();
    }
}
