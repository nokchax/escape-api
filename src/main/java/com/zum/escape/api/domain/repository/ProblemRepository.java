package com.zum.escape.api.domain.repository;

import com.zum.escape.api.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
