package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.ProblemSolveHistory;
import com.nokchax.escape.problem.domain.QProblemSolveHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProblemSolveHistoryRepositoryImpl implements ProblemSolveHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QProblemSolveHistory problemSolveHistory = QProblemSolveHistory.problemSolveHistory;

    public ProblemSolveHistoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProblemSolveHistory> findAllHistoryWithOrdering() {

        return queryFactory.select(problemSolveHistory)
                .from(problemSolveHistory)
                .orderBy(problemSolveHistory.totalCount.desc())
                .orderBy(problemSolveHistory.hardCount.desc())
                .orderBy(problemSolveHistory.mediumCount.desc())
                .orderBy(problemSolveHistory.easyCount.desc())
                .fetch();
    }
}
