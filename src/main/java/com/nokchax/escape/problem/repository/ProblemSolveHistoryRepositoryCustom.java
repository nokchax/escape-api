package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.dto.ProblemSolveHistoryDto;

import java.util.List;

public interface ProblemSolveHistoryRepositoryCustom {
    List<ProblemSolveHistoryDto> findAllHistoryWithOrdering();
}
