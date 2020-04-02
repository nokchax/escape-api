package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;

import java.util.List;

public interface ProblemRepositoryCustom {
    List<ProblemSolveUserDto> findProblemSolveUser(Long problemNo);

    /** 넘겨받은 문제리스트 중에서 이미 저장된 문제의 수를 리턴 */
    List<Problem> checkSolvedProblemCount(String id, List<String> titles);
}
