package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.ProblemSolveHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemSolveHistoryRepository extends JpaRepository<ProblemSolveHistory, String> {
    List<ProblemSolveHistory> findAllByOrderByTotalCountDescHardCountDescMediumCountDescEasyCountDesc();

    @Query(value = "SELECT solveHistory FROM ProblemSolveHistory solveHistory ORDER BY totalCount DESC, hardCount DESC, mediumCount DESC, easyCount DESC" )
    List<ProblemSolveHistory> findAllWithOrdering();
}
