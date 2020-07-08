package com.nokchax.escape.problem.repository;

import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.QProblem;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import com.nokchax.escape.problem.dto.QProblemDto;
import com.nokchax.escape.problem.dto.QProblemSolveUserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
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

    @Override
    public List<Problem> findSolvedButNotSavedYetProblems(String id, List<String> titles) {
        if (StringUtils.isEmpty(id) || CollectionUtils.isEmpty(titles)) {
            return Collections.emptyList();
        }

        QProblem subProblem = new QProblem("subProblem");
        QSolvedProblem subSolvedProblem = new QSolvedProblem("subSolvedProblem");

        // 결국 가져오는건 사용자가 풀었는데 저장이 안된 문제들
        return queryFactory.selectFrom(problem)
                .where(
                        // 우선 사용자가 풀었고 이미 저장된 문제들은 제외
                        problem.id.notIn(
                            select(subSolvedProblem.problem.id)
                                .from(subSolvedProblem)
                                .where(subSolvedProblem.user.id.eq(id))
                        )
                .and(
                        // 크롤한 데이터 기준으로 푼 문제들은 포함해야함
                        problem.id.in(
                            select(subProblem.id)
                                    .from(subProblem)
                                    .where(subProblem.title.in(titles))
                        )
                ))
                .fetch();
    }

    @Override
    public List<Problem> findSolvedButRemoveFromApi(String id, List<String> titles) {
        if (StringUtils.isEmpty(id) || CollectionUtils.isEmpty(titles)) {
            return Collections.emptyList();
        }
        return null;
    }
}
