package com.nokchax.escape.point.repository;


import com.nokchax.escape.point.domain.QPoint;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.user.domain.QUser;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;

public class PointRepositoryImpl implements PointRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QPoint point = QPoint.point1;
    private final QUser user = QUser.user;

    public PointRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<PointDto> findAllUserPoint() {

        return queryFactory.select(
                    bean(
                            PointDto.class,
                            user.id.as("userId"),
                            new CaseBuilder().when(point.point.sum().isNull())
                                    .then(0)
                                    .otherwise(point.point.sum())
                                    .as("point")
                    )
                )
                .from(user)
                .leftJoin(point).on(user.id.eq(point.user.id))
                .groupBy(user.id)
                .orderBy(point.point.sum().desc())
                .fetch();
    }

    @Override
    public List<PointDto> findUserPointByUserId(List<String> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }

        return queryFactory.select(
                    bean(
                            PointDto.class,
                            user.id.as("userId"),
                            new CaseBuilder().when(point.point.sum().isNull())
                                    .then(0)
                                    .otherwise(point.point.sum())
                                    .as("point")
                    )
                )
                .from(user)
                .leftJoin(point).on(user.id.eq(point.user.id))
                .where(user.id.in(userIds))
                .groupBy(user.id)
                .orderBy(point.point.sum().desc())
                .fetch();
    }

    @Override
    public List<PointDto> findAllPenaltyUsers() {

        return queryFactory.select(
                    bean(
                            PointDto.class,
                            user.id.as("userId"),
                            new CaseBuilder().when(point.point.sum().isNull())
                                    .then(0)
                                    .otherwise(point.point.sum())
                                    .as("point")
                    )
                )
                .from(user)
                .leftJoin(point).on(user.id.eq(point.user.id))
                .groupBy(user.id)
                .having(point.point.sum().lt(0))
                .orderBy(point.point.sum().desc())
                .fetch();
    }
}
