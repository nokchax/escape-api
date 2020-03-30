package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.QProblem;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import com.nokchax.escape.problem.dto.QProblemDto;
import com.nokchax.escape.problem.dto.QProblemSolveUserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProblemRepositoryImpl implements ProblemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QProblem problem = QProblem.problem;
    QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;

    public ProblemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProblemSolveUserDto> findProblemSolveUser(Long problemNo) {

        return queryFactory.select(
                        new QProblemSolveUserDto(
                                new QProblemDto(problem.viewId, problem.title, problem.titleSlug, problem.difficulty),
                                solvedProblem.user.id,
                                solvedProblem.solvedTime,
                                solvedProblem.updatedTime
                        )
                )
                .from(problem)
                .leftJoin(problem.solvedProblems, solvedProblem)
                .where(problem.id.eq(problemNo))
                .orderBy(
                        solvedProblem.solvedTime.asc(),
                        solvedProblem.user.id.asc()
                )
                .fetch();
    }
}
