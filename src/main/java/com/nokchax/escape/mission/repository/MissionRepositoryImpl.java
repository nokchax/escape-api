package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class MissionRepositoryImpl implements MissionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QMission mission = QMission.mission;
    QEntry entry = QEntry.entry;

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
}
