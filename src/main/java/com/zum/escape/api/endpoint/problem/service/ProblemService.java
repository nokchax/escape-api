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
        List<Problem> problems = getProblems();

/*        problems.forEach(
                problem -> problemRepository.findById(problem.getId())
                        .orElse(problemRepository.save(problem))
        );*/
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

    public Set<Problem> toProblem(CrawledUserInfo crawledUserInfo) {
        Set<Problem> problems = new HashSet<>();

        crawledUserInfo.getSolvedProblems().forEach(name -> problems.add(cachedProblems.get(name)));

        return problems;
    }
}
