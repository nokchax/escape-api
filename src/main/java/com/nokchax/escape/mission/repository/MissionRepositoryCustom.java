package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;

import java.util.List;

public interface MissionRepositoryCustom {
    Mission findMissionAndEntry(Long missionId);
}
