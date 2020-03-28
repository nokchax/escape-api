package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;

import java.util.List;

public interface SolvedProblemRepositoryCustom {
    List<SolvedProblemSummaryDto> getSolvedProblemOfLatestMissionUser();
}
