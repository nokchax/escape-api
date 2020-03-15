package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.ProblemSolveHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSolveHistoryRepository extends JpaRepository<ProblemSolveHistory, String> {
}
