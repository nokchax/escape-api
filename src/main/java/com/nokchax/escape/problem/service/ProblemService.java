package com.nokchax.escape.problem.service;

import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final UserRepository userRepository;
    private final EntryService entryService;

    public boolean checkSolvedProblemExist(User user, CrawledUserInfo crawledUserInfo) {
        // 푼 문제들의 title 만 추리기
        List<String> titles = crawledUserInfo.getSolvedProblems()
                .stream()
                .map(ProblemSolveInfo::getProblemTitle)
                .collect(Collectors.toList());

        // 크롤한 문제들 중에서(이 문제들은 푼 문제들) DB에 저장이 안된 문제들만 가져오기
        List<Problem> notSavedSolvedProblems = problemRepository.checkSolvedProblemCount(user.getId(), titles);

        // 저장이 안된 푼 문제들을 저장해야 하므로 entity 생성
        Set<SolvedProblem> solvedProblems = notSavedSolvedProblems.stream()
                .map(problem -> new SolvedProblem(user, problem, crawledUserInfo))
                .collect(Collectors.toSet());

        // solved problem entity 에 mission 정보를 추가하는 작업
        missionService.fillOutSolvedProblemMissionInfo(solvedProblems);

        // 저장하기!
        boolean result = user.addSolvedProblems(solvedProblems);
        if(result) {
            userRepository.save(user);
        }

        return result;
    }

    public List<ProblemDto> checkProblemUpdated(List<CrawledProblemInfo> crawledProblems) {
        // 모든 문제 가져오기
        Map<Long, Problem> problems = findAllProblemsAsMap();

        // 문제 업데이트 체크해서 업데이트 된 정보만 추리고, 문제 엔티티화
        List<Problem> updatedProblems = crawledProblems.stream()
                .filter(crawledProblem -> problems.get(crawledProblem.getProblemId()).isUpdated(crawledProblem))
                .map(CrawledProblemInfo::toProblem)
                .collect(Collectors.toList());

        // 저장하기
        problemRepository.saveAll(updatedProblems);

        // 업데이트 된 문제들만 Dto로 변경하여 리턴하기
        return updatedProblems.stream()
                .map(Problem::toDto)
                .collect(Collectors.toList());
    }

    private Map<Long, Problem> findAllProblemsAsMap() {
        return problemRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Problem::getId, Function.identity()));
    }
}
