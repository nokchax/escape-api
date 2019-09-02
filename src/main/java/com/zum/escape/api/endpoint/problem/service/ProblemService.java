package com.zum.escape.api.endpoint.problem.service;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.domain.repository.ProblemRepository;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final LeetCodeService leetCodeService;
    private final ProblemRepository problemRepository;

    public List<Problem> getProblems() {
        ProblemResponse problemResponse = leetCodeService.getProblems();

        return problemResponse.toProblemList();
    }

    public void saveOrUpdateProblems() {
        List<Problem> problems = getProblems();

        problems.forEach(
                problem -> problemRepository.findById(problem.getId())
                        .orElse(problemRepository.save(problem))
        );
    }
}
