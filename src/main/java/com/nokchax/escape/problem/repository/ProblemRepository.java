package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
