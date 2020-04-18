package com.nokchax.escape.problem.service;

import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final MissionService missionService;

    @Transactional
    public boolean checkSolvedProblemExist(User user, CrawledUserInfo crawledUserInfo) {
        // 푼 문제들의 title 만 추리기
        List<String> titles = crawledUserInfo.getSolvedProblems()
                .stream()
                .map(ProblemSolveInfo::getProblemTitle)
                .collect(Collectors.toList());

        // 크롤한 문제들 중에서(이 문제들은 푼 문제들) DB에 저장이 안된 문제들만 가져오기
        List<Problem> notSavedSolvedProblems = problemRepository.findSolvedButNotSavedYetProblems(user.getId(), titles);

        // 저장이 안된 푼 문제들을 저장해야 하므로 entity 생성
        Set<SolvedProblem> solvedProblems = notSavedSolvedProblems.stream()
                .map(problem -> new SolvedProblem(user, problem, crawledUserInfo))
                .collect(Collectors.toSet());

        // solved problem entity 에 mission 정보를 추가하는 작업
        missionService.fillOutSolvedProblemMissionInfo(solvedProblems);

        // 저장하기!
        return user.addSolvedProblems(solvedProblems);
    }

    public List<ProblemDto> checkProblemIsNewOrUpdated(List<CrawledProblemInfo> crawledProblems) {
        // 모든 문제 가져오기
        Map<Long, Problem> problems = findAllProblemsAsMap();

        // 문제 업데이트 체크해서 업데이트 된 정보만 추리고, 문제 엔티티화
        List<Problem> newOrUpdatedProblems = crawledProblems.stream()
                .filter(crawledProblem -> checkNewOrUpdated(problems, crawledProblem))
                .map(CrawledProblemInfo::toProblem)
                .collect(Collectors.toList());

        // 저장하기
        problemRepository.saveAll(newOrUpdatedProblems);

        // 업데이트 된 문제들만 Dto로 변경하여 리턴하기
        return newOrUpdatedProblems.stream()
                .map(Problem::toDto)
                .collect(Collectors.toList());
    }

    private boolean checkNewOrUpdated(Map<Long, Problem> problems, CrawledProblemInfo crawledProblemInfo) {
        if(!problems.containsKey(crawledProblemInfo.getProblemId())) {
            return true;
        }

        return problems.get(crawledProblemInfo.getProblemId()).isUpdated(crawledProblemInfo);
    }

    private Map<Long, Problem> findAllProblemsAsMap() {
        return problemRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Problem::getId, Function.identity()));
    }
}
