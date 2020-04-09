package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.domain.QEntry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.dto.QEntryDto;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QMission;
import com.nokchax.escape.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

public class EntryRepositoryImpl implements EntryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QEntry entry = QEntry.entry;
    private QUser user = QUser.user;
    private QMission mission = QMission.mission;

    public EntryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Entry> getLatestEntry() {

        return queryFactory.selectFrom(entry)
                .where(entry.mission.id.eq(select(mission.id.max()).from(mission)))
                .fetch();
    }

    @Override
    public List<EntryDto> findUsersInLatestMissionByUserId(List<String> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }

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
                .where(
                        entry.mission.id.eq(select(mission.id.max()).from(mission))
                        .and(entry.user.id.in(userIds))
                )
                .fetch();
    }

    @Override
    public List<Entry> findAllUserIncompleteLastMission() {
        Mission lastMission = queryFactory.selectFrom(mission)
                .orderBy(mission.id.desc())
                .limit(1)
                .offset(1)
                .fetchFirst();

        return queryFactory.select(entry)
                .from(entry)
                .leftJoin(entry.user, user)
                .leftJoin(entry.mission, mission)
                .where(
                        entry.mission.id.eq(lastMission.getId())
                        .and(entry.score.lt(lastMission.getGoalScore()))
                )
                .fetch();
    }
}
