package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.mission.domain.Mission;

import java.util.List;

public interface EntryRepositoryCustom {
    /** * 최신 미션({@link Mission})을 수행하고 있는 사용자({@link Entry})를 가져온다. */
    List<Entry> getLatestEntry();

    /** * 이전 미션({@link Mission})을 수행했던 사용자({@link Entry})를 가져온다. */
    List<Entry> getLastEntry();

    /** * 최신 미션을 수행하고 있는 사용자 중 원하는 사용자만 가져온다. */
    List<EntryDto> findUsersInLatestMissionByUserId(List<String> userIds);

    /** * 지난 미션을 수행했던 사용자중 미션을 완료 못한 사용자만 가져온다. */
    List<Entry> findAllUserIncompleteLastMission();
}
