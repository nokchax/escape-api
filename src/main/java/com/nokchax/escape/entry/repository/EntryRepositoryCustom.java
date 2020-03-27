package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;

import java.util.List;

public interface EntryRepositoryCustom {
    /** * 최신 미션({@link Mission})을 수행하고 있는 사용자({@link Entry})를 가져온다. */
    List<Entry> getLatestEntry();
}
