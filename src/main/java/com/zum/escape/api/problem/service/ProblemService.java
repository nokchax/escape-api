package com.zum.escape.api.problem.service;

import com.zum.escape.api.problem.domain.entity.Problem;
import com.zum.escape.api.problem.repository.ProblemRepository;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.Submission;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService {
    private final LeetCodeService leetCodeService;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private ConcurrentHashMap<String, Problem> cachedProblems = new ConcurrentHashMap<>();

    @Value("${master.id}")
    private String id;
    @Value("${master.pw}")
    private String pw;
    @Value("${master.name}")
    private String name;

    public List<Problem> getProblems() {
        ProblemResponse problems = leetCodeService.getProblems();
        if(problems == null) {
            return Collections.emptyList();
        }
        return problems.toProblemList();
    }

    public List<Problem> getProblemsUsingLogin() {
        List<User> users = userRepository.findAll();
        int pickIdx = ThreadLocalRandom.current().nextInt(users.size());

        ProblemResponse problems = leetCodeService.getProblems(users.get(pickIdx));
        if(problems == null) {
            return Collections.emptyList();
        }
        return problems.toProblemList();
    }

    public void saveOrUpdateProblemsLoginBase() {
        updateProblems(getProblemsUsingLogin());
    }

    private void updateProblems(List<Problem> notUpdatedProblems) {
        if(notUpdatedProblems.isEmpty()) {
            return;
        }

        for(Problem problem : notUpdatedProblems) {
            System.out.println(problem);
            problemRepository.save(problem);
        }

        ConcurrentHashMap<String, Problem> updatedConcurrentHashMap = new ConcurrentHashMap<>(cachedProblems);
        notUpdatedProblems.forEach(
                problem -> updatedConcurrentHashMap.put(problem.getTitle(), problem)
        );

        cachedProblems = updatedConcurrentHashMap;
    }

    public void saveOrUpdateProblems() {
        updateCache();

        updateProblems(getProblems());
    }

    private void updateCache() {
        List<Problem> problems = problemRepository.findAll();
        log.info("problem lists size : {}", problems.size());
        ConcurrentHashMap<String, Problem> newCache = new ConcurrentHashMap<>();
        problems.forEach(problem -> newCache.put(problem.getTitle(), problem));
        cachedProblems = newCache;
    }

    public Problem findProblem(Long problemNo) {
        return cachedProblems.values()
                .stream()
                .filter(x -> problemNo.equals(x.getViewId()))
                .findFirst().orElse(null);
    }

    public Set<Problem> toProblem(CrawledUserInfo crawledUserInfo) {
        Set<Submission> submissions = crawledUserInfo.getSolvedProblems();

        return submissions.stream()
                .filter(submission -> cachedProblems.containsKey(submission.getProblemTitle()))
                .map(submission -> cachedProblems.get(submission.getProblemTitle()))
                .collect(Collectors.toSet());
    }

    public Set<Problem> toProblem(List<String> problemNames) {
        return problemNames.stream()
                .filter(title -> cachedProblems.containsKey(title))
                .map(title -> cachedProblems.get(title))
                .collect(Collectors.toSet());
    }
}
