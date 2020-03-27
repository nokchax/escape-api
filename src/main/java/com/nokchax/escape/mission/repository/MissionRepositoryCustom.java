package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;

public interface MissionRepositoryCustom {
    Mission findMissionAndEntry(Long missionId);
    Mission findLatestMissionWithEntry();
    Mission findMissionWithEntryAndUser();
}
