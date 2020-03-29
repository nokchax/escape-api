package com.nokchax.escape.user.repository;


import com.nokchax.escape.user.domain.QUser;
import com.nokchax.escape.user.domain.QUserPointHistory;
import com.nokchax.escape.user.domain.UserPointHistory;
import com.nokchax.escape.user.dto.UserPointDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zum.escape.api.users.dto.UserProblemSolveDto;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;

public class UserPointHistoryRepositoryImpl implements UserPointHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QUserPointHistory userPointHistory = QUserPointHistory.userPointHistory;
    private QUser user = QUser.user;

    public UserPointHistoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<UserPointDto> findAllUserPointHistoryWithOrdering() {

        return queryFactory.select(
                    bean(
                            UserPointDto.class,
                            user.id.as("userId"),
                            new CaseBuilder().when(userPointHistory.point.sum().isNull())
                                    .then(0)
                                    .otherwise(userPointHistory.point.sum())
                                    .as("point")
                    )
                )
                .from(user)
                .leftJoin(userPointHistory).on(user.id.eq(userPointHistory.user.id))
                .groupBy(user.id)
                .orderBy(userPointHistory.point.sum().desc())
                .fetch();
    }
}
