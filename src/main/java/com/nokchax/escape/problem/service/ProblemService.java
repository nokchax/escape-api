package com.nokchax.escape.problem.service;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final MissionService missionService;
    private final UserRepository userRepository;

    public boolean checkSolvedProblemExist(User user, CrawledUserInfo crawledUserInfo) {
        // 푼 문제들의 title 만 추리기
        List<String> titles = crawledUserInfo.getSolvedProblems()
                .stream()
                .map(ProblemSolveInfo::getProblemTitle)
                .collect(Collectors.toList());

        // 크롤한 문제들 중에서(이 문제들은 푼 문제들) DB에 저장이 안된 문제들만 가져오기
        List<Problem> notSavedSolvedProblems = problemRepository.checkSolvedProblemCount(user.getId(), titles);

        System.out.println("Not solved problems : " + notSavedSolvedProblems.size());
        notSavedSolvedProblems.forEach(System.out::println);

        // 저장이 안된 푼 문제들을 저장해야 하므로 entity 생성
        Set<SolvedProblem> solvedProblems = notSavedSolvedProblems.stream()
                .map(problem -> new SolvedProblem(user, problem, crawledUserInfo))
                .collect(Collectors.toSet());

        // solved problem entity 에 mission 정보를 추가하는 작업
        missionService.fillOutSolvedProblemMissionInfo(solvedProblems);

        // 저장하기!
        boolean result = user.addSolvedProblems(solvedProblems);
        if(result) {
            System.out.println("Before save");
            userRepository.save(user);
            System.out.println("After save");
        }

        return result;
    }
}
