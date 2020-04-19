package com.nokchax.escape.user.repository;

import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.user.domain.QUser;
import com.nokchax.escape.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QUser user = QUser.user;
    private final QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;
    private final QMission mission = QMission.mission;
    private final QEntry entry = QEntry.entry;

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
    public Optional<User> findOneByUserId(String target) {
        if (StringUtils.isEmpty(target)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                        .where(user.id.eq(target))
                        .fetchOne()
        );
    }

    @Override
    public List<User> findByUserIdWithSolvedProblems(String target) {
        if(StringUtils.isEmpty(target)) {
            return Collections.emptyList();
        }

        return queryFactory.select(user)
                .from(user)
                .leftJoin(user.solvedProblem, solvedProblem)
                .fetchJoin()
                .where(target.equalsIgnoreCase("all") ? null : user.id.eq(target))
                .fetch();
    }

    @Override
    public Optional<User> findByTelegramId(String target) {
        if(StringUtils.isEmpty(target)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                queryFactory.select(user)
                        .from(user)
                        .leftJoin(user.solvedProblem, solvedProblem)
                        .fetchJoin() //fetchjoin 이 없을때는 연관된 객체를 조회할때 다시한번 조회쿼리를 날림
                        .where(user.telegramId.eq(target))
                        .fetchOne()
        );
    }

    @Override
    public List<User> findUsersNotInLatestMission() {
        QMission missionSub = new QMission("missionSub");

        List<String> ids = queryFactory.select(entry.user.id)
                .from(entry)
                .leftJoin(entry.mission, mission)
                .where(entry.mission.id.eq(select(missionSub.id.max()).from(missionSub)))
                .fetch();

        return queryFactory.selectFrom(user)
                .where(user.id.notIn(ids))
                .fetch();
    }
}
