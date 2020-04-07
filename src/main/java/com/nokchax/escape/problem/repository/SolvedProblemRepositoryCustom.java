package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.user.domain.User;

import java.util.List;

public interface SolvedProblemRepositoryCustom {
    List<SolvedProblemSummaryDto> getSolvedProblemOfLatestMissionUser(List<String> users);
}
