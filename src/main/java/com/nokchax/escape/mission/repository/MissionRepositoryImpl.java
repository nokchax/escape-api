package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.mission.dto.MissionEntryDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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
