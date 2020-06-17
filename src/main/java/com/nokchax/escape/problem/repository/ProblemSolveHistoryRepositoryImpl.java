package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.QProblemSolveHistory;
import com.nokchax.escape.problem.dto.ProblemSolveHistoryDto;
import com.nokchax.escape.problem.dto.QProblemSolveHistoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProblemSolveHistoryRepositoryImpl implements ProblemSolveHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QProblemSolveHistory problemSolveHistory = QProblemSolveHistory.problemSolveHistory;

    public ProblemSolveHistoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProblemSolveHistoryDto> findAllHistoryWithOrdering() {

        return queryFactory.select(
                        new QProblemSolveHistoryDto(
                                problemSolveHistory.userId,
                                problemSolveHistory.totalCount,
                                problemSolveHistory.hardCount,
                                problemSolveHistory.mediumCount,
                                problemSolveHistory.easyCount
                        )
                )
                .from(problemSolveHistory)
                .orderBy(
                        problemSolveHistory.totalCount.desc(),
                        problemSolveHistory.hardCount.desc(),
                        problemSolveHistory.mediumCount.desc(),
                        problemSolveHistory.easyCount.desc()
                )
                .fetch();
    }
}
