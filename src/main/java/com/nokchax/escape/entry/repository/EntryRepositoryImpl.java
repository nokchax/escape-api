package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

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
                .where(entry.mission.id.eq(
                        select(mission.id.max()).from(mission)
                )).fetch();
    }
}
