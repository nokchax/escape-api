package com.nokchax.escape.entry.repository;

import com.nokchax.escape.mission.domain.QEntry;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class EntryRepositoryImpl implements EntryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QEntry entry = QEntry.entry;

    public EntryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
