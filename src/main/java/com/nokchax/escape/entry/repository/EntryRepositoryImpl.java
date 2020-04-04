package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.dto.QEntryDto;
import com.nokchax.escape.mission.domain.QMission;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.selectOne;

public class EntryRepositoryImpl implements EntryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QEntry entry = QEntry.entry;
    QMission mission = QMission.mission;

    public EntryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Entry> getLatestEntry() {

        return queryFactory.select(entry)
                .where(entry.mission.id.eq(select(mission.id.max()).from(mission)))
                .fetch();
    }

    @Override
    public List<EntryDto> findAllUserInLatestMission(List<String> userIds) {
        return queryFactory.select(
                    new QEntryDto(
                            entry.mission.id,
                            entry.user.id,
                            entry.score,
                            entry.hard,
                            entry.medium,
                            entry.easy
                    )
                )
                .from(entry)
                .where(entry.mission.id.eq(select(mission.id.max()).from(mission)))
                .fetch();
    }

    @Override
    public List<EntryDto> findAllUserIncompleteLastMission() {
        QMission subMission = new QMission("subMission");

        return queryFactory.select(
                    new QEntryDto(
                            entry.mission.id,
                            entry.user.id,
                            entry.score,
                            entry.hard,
                            entry.medium,
                            entry.easy
                    )
                )
                .from(entry)
                .where(entry.mission.id.in(
                        select(mission.id.max())
                        .from(mission)
                        .where(mission.id.lt(
                                select(subMission.id.max())
                                        .from(subMission)
                                )
                            )
                        )
                )
                .fetch();
    }
}
