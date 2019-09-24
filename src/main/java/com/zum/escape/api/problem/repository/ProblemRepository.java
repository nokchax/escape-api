package com.zum.escape.api.problem.repository;

import com.zum.escape.api.problem.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
