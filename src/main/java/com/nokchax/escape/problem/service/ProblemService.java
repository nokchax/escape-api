package com.nokchax.escape.problem.service;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public boolean checkSolvedProblemExist(User user, CrawledUserInfo crawledUserInfo) {
        List<String> titles = crawledUserInfo.getSolvedProblems()
                .stream()
                .map(ProblemSolveInfo::getProblemTitle)
                .collect(Collectors.toList());

        List<Problem> notSavedSolvedProblems = problemRepository.checkSolvedProblemCount(user.getId(), titles);

        user.addSolvedProblems(notSavedSolvedProblems);

        return false;
    }
}
