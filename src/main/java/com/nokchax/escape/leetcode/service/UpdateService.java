package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public List<EntryDto> updateLatestMission(UpdateCommand.UpdateArgument argument) {
        log.info("UPDATE USER STARTED");
        List<User> users = userService.findByArgument(argument);
/*

        List<CompletableFuture<User>> futures = participants.stream()
                .map(usersService::updateUser)
                .collect(Collectors.toList());


        log.info("WAITING CRAWLING USERS");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .join();
*/

        log.info("UPDATE USER END");
        // update logic
        /*
            1. user page 크롤후 비교 -> 푼 총 문제수에 변화가 없다면 업데이트 스킵 후 리턴
                1-2. 푼 총 문제수에 변화가 있다면, 페이지 크롤후 푼문제 업데이트
                1-3. 문제 업데이트 이후 회원의 solve problem 수 업데이트
                1-4. 업데이트된 problem 수와 크롤한 문제수가 다르다면? 3번으로
            3. login api 크롤후 비교
                3-2. 문제 업데이트 이후 회원의 solve problem 수 업데이트
                3-3. 여기까지 오면 problem 수와 크롤한 문제수가 같을것.
            4. entry return 후 종료
         */

        return entryService.findAllUserInLatestMission(users);
    }

    public List<ProblemDto> updateProblems() {
        /*
            1. api 크롤해서 업데이트 (로그인 크롤)
            2. 기존 problem과 새 문제들 비교 후 업데이트 된 문제들 저장
            3. 업데이트 된 문제들 모두 리턴.
         */

        return Collections.EMPTY_LIST;
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<User> updateUser(User user) throws Exception {
        log.debug("{} : {}", Thread.currentThread().getName(), user.getId());

        // 페이지 크롤
        CrawledUserInfo crawledUserInfo = pageCrawler.crawlUserInfo(user)
                .orElseThrow(() -> new Exception("Fail to crawl from page"));

        if(!problemService.checkSolvedProblemExist(user, crawledUserInfo)) {
            return CompletableFuture.completedFuture(user);
        }

        // 문제 업데이트를 했는데 여전히 푼 문제의 count 수가 동일하지 않다면 login crawl 시도



//        crawledUserInfo.orElseGet()


/*
        if(!user.checkSolveQuestion(crawledUserInfo)) {
            return CompletableFuture.completedFuture(user);
        }


        userProblemRepository.saveAll(user.updateSolvedProblems(crawledUserInfo));

        if(user.isSolvedProblemCountNotCorrect(crawledUserInfo)) {
            log.error("Solved problem count not correct so update : {}\n{}", user, crawledUserInfo);
            updateAllSolvedHistory(user, LocalDateTime.now());
        }
*/

        return CompletableFuture.completedFuture(user);
    }
}
