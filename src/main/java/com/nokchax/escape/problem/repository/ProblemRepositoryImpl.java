package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.QProblem;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import com.nokchax.escape.problem.dto.QProblemDto;
import com.nokchax.escape.problem.dto.QProblemSolveUserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

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

    /*
        sub solved problem 에서 일단 id가 같은 사용자가 푼 문제를 모두 찾아낸다.
        이 서브 쿼리 결과로 문제의 idx값을 찾아낸 다음 title에는 없는 문제들을 다시 추려낸다.
     */
    @Override
    public List<Problem> checkSolvedProblemCount(String id, List<String> titles) {
        QProblem subProblem = new QProblem("subProblem");
        QSolvedProblem subSolvedProblem = new QSolvedProblem("subSolvedProblem");

        return queryFactory.selectFrom(problem)
                .where(
                        problem.id.notIn(
                            select(subSolvedProblem.problem.id)
                                .from(subSolvedProblem)
                                .where(subSolvedProblem.user.id.eq(id))
                        )
                .and(
                        problem.id.in(
                            select(subProblem.id)
                                    .from(subProblem)
                                    .where(subProblem.title.in(titles))
                        )
                ))
                .fetch();
    }
}
