package com.nokchax.escape.problem.repository;

import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nokchax.escape.problem.domain.Difficulty.*;
import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.jpa.JPAExpressions.select;

public class SolvedProblemRepositoryImpl implements SolvedProblemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QMission mission = QMission.mission;
    private QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;

    public SolvedProblemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<SolvedProblemSummaryDto> getSolvedProblemOfLatestMissionUser() {
        return queryFactory.select(
                    bean(
                        SolvedProblemSummaryDto.class,
                        solvedProblem.user.id.as("userId"),
                        solvedProblem.mission.id.as("missionId"),
                        new CaseBuilder().when(solvedProblem.problem.difficulty.eq(HARD))
                                .then(1)
                                .otherwise(0)
                                .sum()
                                .as("hardCount"),
                        new CaseBuilder().when(solvedProblem.problem.difficulty.eq(MEDIUM))
                                .then(1)
                                .otherwise(0)
                                .sum()
                                .as("mediumCount"),
                        new CaseBuilder().when(solvedProblem.problem.difficulty.eq(EASY))
                                .then(1)
                                .otherwise(0)
                                .sum()
                                .as("easyCount")
                    )
                )
                .from(solvedProblem)
                .where(solvedProblem.mission.id.eq(select(mission.id.max()).from(mission)))
                .groupBy(solvedProblem.user)
                .fetch();
    }
}
