package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.QUser;
import com.nokchax.escape.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QUser user = QUser.user;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<User> findByUserId(String target) {

        return queryFactory.selectFrom(user)
                .where(target.equalsIgnoreCase("all") ? null : user.id.eq(target))
                .fetch();
    }

    @Override
    public List<User> findByTelegramId(String target) {

        return queryFactory.selectFrom(user)
                .where(user.telegramId.eq(target))
                .fetch();
    }
}
