package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.QUser;
import com.nokchax.escape.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QUser user = QUser.user;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<User> findByUserId(String target) {
        if(StringUtils.isEmpty(target)) {
            return Collections.emptyList();
        }

        return queryFactory.selectFrom(user)
                .where(target.equalsIgnoreCase("all") ? null : user.id.eq(target))
                .fetch();
    }

    @Override
    public Optional<User> findByTelegramId(String target) {
        if(StringUtils.isEmpty(target)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                        .where(user.telegramId.eq(target))
                        .fetchOne()
        );
    }
}
