package com.zum.escape.api.endpoint.problem.service;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.domain.repository.ProblemRepository;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
        List<Problem> problems = getProblems();

        if(!isUpdated(problems))
            return;

        for(Problem problem : problems) {
            System.out.println(problem);
            if(!problemRepository.existsById(problem.getId()))
                problemRepository.save(problem);
        }

        ConcurrentHashMap<String, Problem> updatedConcurrentHashMap = new ConcurrentHashMap<>();
        problems.forEach(
                problem -> updatedConcurrentHashMap.put(problem.getTitle(), problem)
        );

        cachedProblems = updatedConcurrentHashMap;
    }

    private boolean isUpdated(List<Problem> problems) {
        for(Problem problem : problems)
            if(!cachedProblems.contains(problem.getTitle()))
                return true;

        return false;
    }

    private void updateCache() {
        List<Problem> problems = problemRepository.findAll();
        ConcurrentHashMap<String, Problem> newCache = new ConcurrentHashMap<>();
        problems.forEach(problem -> newCache.put(problem.getTitle(), problem));
        cachedProblems = newCache;
    }

    public Set<Problem> toProblem(CrawledUserInfo crawledUserInfo) {
        Set<Problem> problems = new HashSet<>();

        crawledUserInfo.getSolvedProblems().forEach(name -> problems.add(cachedProblems.get(name)));

        return problems;
    }
}
