package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.domain.SolvedProblemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, SolvedProblemId>, SolvedProblemRepositoryCustom {
}
