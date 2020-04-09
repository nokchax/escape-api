package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;

import java.util.List;

public interface ProblemRepositoryCustom {
    /** 문제 번호를 넘겨받으면 그 문제를 푼 사용자리스트를 리턴 */
    List<ProblemSolveUserDto> findProblemSolveUser(Long problemNo);

    /** 넘겨받은 문제리스트(푼 문제) 중에서 저장이 되지 않은 문제들을 리턴 */
    List<Problem> findSolvedButNotSavedYetProblems(String id, List<String> titles);
}
