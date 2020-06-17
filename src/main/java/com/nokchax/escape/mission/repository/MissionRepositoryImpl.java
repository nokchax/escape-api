package com.nokchax.escape.mission.repository;

import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;

public class MissionRepositoryImpl implements MissionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QMission mission = QMission.mission;
    private final QEntry entry = QEntry.entry;
    private final QUser user = QUser.user;
    private final QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;
    private final QMission missionSub = new QMission("missionSub");

    public MissionRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Mission> findMissionAndEntry(Long missionId) {

        return Optional.ofNullable(
                queryFactory.select(mission)
                        .from(mission)
                        .innerJoin(mission.entry, entry)
                        .fetchJoin()
                        .where(mission.id.eq(missionId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Mission> findLatestMissionWithEntry() {

        return Optional.ofNullable(
                queryFactory.select(mission)
                        .from(mission)
                        .leftJoin(mission.entry, entry)
                        .fetchJoin()
                        .where(mission.id.eq(select(missionSub.id.max()).from(missionSub)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Mission> findMissionWithEntryAndUser() {

        return Optional.ofNullable(
                queryFactory.select(mission)
                        .from(mission)
                        .leftJoin(mission.entry, entry)
                            .fetchJoin()
                        .leftJoin(entry.user, user)
                            .fetchJoin()
                        .leftJoin(user.solvedProblem, solvedProblem)
                            .fetchJoin()
                        .where(mission.id.eq(select(missionSub.id.max()).from(missionSub)))
                        .fetchOne()
        );
    }
}
