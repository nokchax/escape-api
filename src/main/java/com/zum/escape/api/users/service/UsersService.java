package com.zum.escape.api.users.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.repository.UserProblemRepository;
import com.zum.escape.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final ProblemService problemService;
    private final UserRepository userRepository;
    private final LeetCodeService leetCodeService;
    private final UserProblemCrawlService userProblemCrawlService;
    private final UserProblemRepository userProblemRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public String addUser(List<String> args) {
        User newUser = new User(args);

        if(userRepository.existsById(newUser.getId()))
            return "User already exists";

        //CrawledUserInfo crawledUserInfo = getCrawledUserInfo(newUser.getId());
        //newUser.updateSolvedProblems(crawledUserInfo);

        userRepository.save(newUser);
        updateAllSolvedHistory(newUser, LocalDateTime.of(2010, 1, 1, 0, 0));
        userProblemRepository.saveAll(newUser.getSolvedProblem());

        return "Register complete";
    }

    public List<Problem> updateAllSolvedHistory(User user, LocalDateTime updateTime) {
        ProblemResponse userProblems = null;
        try {
            userProblems = userProblemCrawlService.getUserProblems(user);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        if(userProblems == null)
            return Collections.emptyList();

        List<String> problemNames = userProblems.toProblemNames();
        Set<Problem> problems = problemService.toProblem(problemNames);

        userProblemRepository.saveAll(user.updateSolvedProblems(problems, updateTime));
        return Collections.emptyList();
    }

    public User updateUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        updateUser(user.get());

        return user.get();
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<User> updateUser(User user) {

        log.info("{} : {}", Thread.currentThread().getName(), user.getId());

        CrawledUserInfo crawledUserInfo = getCrawledUserInfo(user.getId());
        if(!user.checkSolveQuestion(crawledUserInfo))
            return CompletableFuture.completedFuture(user);


        log.info("UPDATE USERDATA : {}", user.getId());
        userProblemRepository.saveAll(user.updateSolvedProblems(crawledUserInfo));
        log.info("UPDATE USERDATA ENDS: {}", user.getId());

        if(user.isSolvedProblemCountNotCorrect(crawledUserInfo)) {
            log.error("Solved problem count not correct so update : {}\n{}", user, crawledUserInfo);
            updateAllSolvedHistory(user, LocalDateTime.now());
        }

        return CompletableFuture.completedFuture(user);
    }


    private CrawledUserInfo getCrawledUserInfo(String userId) {
        log.info("Start crawl user : {}", userId);
        try {
            CrawledUserInfo crawledUserInfo = leetCodeService.findUser(userId);
            crawledUserInfo.setProblems(problemService.toProblem(crawledUserInfo));
            log.info("{} : {}", userId, crawledUserInfo);
            return crawledUserInfo;
        } catch (IOException e) {
            log.error("Fail to find user");
        }
        return CrawledUserInfo.NOT_UPDATED;
    }
}
