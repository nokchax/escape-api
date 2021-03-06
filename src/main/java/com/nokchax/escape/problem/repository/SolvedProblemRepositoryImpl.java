package com.nokchax.escape.problem.repository;

import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QProblem;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.dto.QSolvedProblemSummaryDto;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.nokchax.escape.problem.domain.Difficulty.*;
import static com.querydsl.jpa.JPAExpressions.select;

public class SolvedProblemRepositoryImpl implements SolvedProblemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QEntry entry = QEntry.entry;
    private final QMission missionSub = new QMission("missionSub");
    private final QProblem problem = QProblem.problem;
    private final QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;

    public SolvedProblemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<SolvedProblemSummaryDto> findSolvedProblemOfLatestMissionByUserId(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }

        Long latestMissionId = queryFactory.select(missionSub.id.max())
                .from(missionSub)
                .fetchOne();

        if (latestMissionId == null) {
            throw new IllegalArgumentException("Latest mission is not exist");
        }

        return queryFactory.select(
                        new QSolvedProblemSummaryDto(
                                entry.user.id.as("userId"),
                                Expressions.constant(latestMissionId),
                                new CaseBuilder().when(
                                                solvedProblem.problem.difficulty.ne(HARD)
                                                .or(solvedProblem.problem.difficulty.isNull())
                                        )
                                        .then(0)
                                        .otherwise(1)
                                        .sum()
                                        .as("hardCount"),
                                new CaseBuilder().when(
                                                solvedProblem.problem.difficulty.ne(MEDIUM)
                                                .or(solvedProblem.problem.difficulty.isNull())
                                        )
                                        .then(0)
                                        .otherwise(1)
                                        .sum()
                                        .as("mediumCount"),
                                new CaseBuilder().when(
                                                solvedProblem.problem.difficulty.ne(EASY)
                                                .or(solvedProblem.problem.difficulty.isNull())
                                        )
                                        .then(0)
                                        .otherwise(1)
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
                        entry.mission.id.eq(latestMissionId)
                        .and(userIds.isEmpty() ? null : entry.user.id.in(userIds))
                )
                .groupBy(entry.user.id)
                .fetch();
    }

    @Override
    public List<SolvedProblem> findSolvedProblemsByUserId(String userId) {
        return queryFactory.selectFrom(solvedProblem)
                .where(solvedProblem.user.id.eq(userId))
                .fetch();
    }
}
