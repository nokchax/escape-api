package com.zum.escape.api.endpoint.problem.service;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.domain.repository.ProblemRepository;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.Submission;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final LeetCodeService leetCodeService;
    private final ProblemRepository problemRepository;
    private ConcurrentHashMap<String, Problem> cachedProblems = new ConcurrentHashMap<>();

    public List<Problem> getProblems() {
        ProblemResponse problemResponse = leetCodeService.getProblems();

        return problemResponse.toProblemList();
    }

    public void saveOrUpdateProblems() {
        updateCache();

        List<Problem> notUpdatedProblems = getNotUpdatedProblems(getProblems());
        if(notUpdatedProblems.isEmpty())
            return;

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

    private List<Problem> getNotUpdatedProblems(List<Problem> problems) {
        List<Problem> notUpdatedList = new ArrayList<>();
        for(Problem problem : problems) {
            if(!cachedProblems.containsKey(problem.getTitle())) {
                notUpdatedList.add(problem);
            }
        }

        return notUpdatedList;
    }

    private void updateCache() {
        List<Problem> problems = problemRepository.findAll();
        System.out.println(problems.size());
        ConcurrentHashMap<String, Problem> newCache = new ConcurrentHashMap<>();
        problems.forEach(problem -> newCache.put(problem.getTitle(), problem));
        cachedProblems = newCache;
    }

    public Problem findProblem(String problemName) {
        Optional<Problem> problem = cachedProblems.values()
                .stream()
                .filter(x -> problemName.equalsIgnoreCase(x.getTitle()))
                .findFirst();

        return problem.orElse(null);
    }

    public Set<Problem> toProblem(CrawledUserInfo crawledUserInfo) {
        Set<Problem> problems = new HashSet<>();

        Set<Submission> submissions = crawledUserInfo.getSolvedProblems();
        submissions.forEach(submission -> problems.add(cachedProblems.get(submission.getProblemTitle())));

        return problems;
    }

    public Set<Problem> toProblem(List<String> problemNames) {
        return problemNames.stream()
                .filter(title -> cachedProblems.containsKey(title))
                .map(title -> cachedProblems.get(title))
                .collect(Collectors.toSet());
    }

}
