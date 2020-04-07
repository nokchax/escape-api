package com.nokchax.escape.problem.repository;

import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QProblem;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.user.domain.QUser;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nokchax.escape.problem.domain.Difficulty.*;
import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.jpa.JPAExpressions.select;

public class SolvedProblemRepositoryImpl implements SolvedProblemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QUser user = QUser.user;
    private QEntry entry = QEntry.entry;
    private QMission mission = QMission.mission;
    private QMission missionSub = new QMission("missionSub");
    private QProblem problem = QProblem.problem;
    private QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;

    public SolvedProblemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<SolvedProblemSummaryDto> getSolvedProblemOfLatestMissionUser(List<String> userIds) {
        return queryFactory.select(
                        bean(
                                SolvedProblemSummaryDto.class,
                                entry.user.id.as("userId"),
                                entry.mission.id.as("missionId"),
                                new CaseBuilder().when(solvedProblem.problem.difficulty.ne(HARD).or(solvedProblem.problem.difficulty.isNull()))
                                        .then(0L)
                                        .otherwise(1L)
                                        .sum()
                                        .as("hardCount"),
                                new CaseBuilder().when(solvedProblem.problem.difficulty.ne(MEDIUM).or(solvedProblem.problem.difficulty.isNull()))
                                        .then(0L)
                                        .otherwise(1L)
                                        .sum()
                                        .as("mediumCount"),
                                new CaseBuilder().when(solvedProblem.problem.difficulty.ne(EASY).or(solvedProblem.problem.difficulty.isNull()))
                                        .then(0L)
                                        .otherwise(1L)
                                        .sum()
                                        .as("easyCount")
                        )
                )
                .from(solvedProblem)
                .rightJoin(entry)
                    .on(
                            solvedProblem.mission.id.eq(entry.mission.id)
                            .and(solvedProblem.user.id.eq(entry.user.id))
                )
                .leftJoin(solvedProblem.problem, problem)
                .where(
                        entry.mission.id.eq(select(missionSub.id.max()).from(missionSub))
                        .and(userIds.isEmpty() ? null : entry.user.id.in(userIds))
                )
                .groupBy(entry.user)
                .fetch();
    }
}
