package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.dto.ProblemSolveUserDto;

import java.util.List;

public interface ProblemRepositoryCustom {
    List<ProblemSolveUserDto> findProblemSolveUser(Long problemNo);
}
