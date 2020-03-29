package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.ProblemSolveHistory;

import java.util.List;

public interface ProblemSolveHistoryRepositoryCustom {
    List<ProblemSolveHistory> findAllHistoryWithOrdering();
}
