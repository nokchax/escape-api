package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.problem.domain.QSolvedProblem;
import com.nokchax.escape.user.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.querydsl.jpa.JPAExpressions.select;

public class MissionRepositoryImpl implements MissionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QMission mission = QMission.mission;
    QEntry entry = QEntry.entry;
    QUser user = QUser.user;
    QSolvedProblem solvedProblem = QSolvedProblem.solvedProblem;

    public MissionRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Mission findMissionAndEntry(Long missionId) {

        return queryFactory.select(mission)
                .from(mission)
                .innerJoin(mission.entry, entry)
                    .fetchJoin()
                .where(mission.id.eq(missionId))
                .fetchOne();
    }

    @Override
    public Mission findLatestMissionWithEntry() {
        QMission missionSub = new QMission("missionSub");

        return queryFactory.select(mission)
                .from(mission)
                .innerJoin(mission.entry, entry)
                    .fetchJoin()
                .where(mission.id.eq(
                        select(missionSub.id.max())
                                .from(missionSub)
                )).fetchOne();
    }

    @Override
    public Mission findMissionWithEntryAndUser() {
        // User를 가져올 필요가 있을까???
        // User가 푼 데이터 수를 업데이트 하는건 따로 호출하는게 낫지 않을까
        QMission missionSub = new QMission("missionSub");

        return queryFactory.select(mission)
                .from(mission)
                .innerJoin(mission.entry, entry)
                    .fetchJoin()
                .innerJoin(entry.user, user)
                    .fetchJoin()
                .innerJoin(user.solvedProblem, solvedProblem)
                    .fetchJoin()
                .where(
                        mission.id.eq(select(missionSub.id.max()).from(missionSub))
                        .and(
                            solvedProblem.mission.id.eq(select(missionSub.id.max()).from(missionSub)
                        )
                )).fetchOne();
    }
}
