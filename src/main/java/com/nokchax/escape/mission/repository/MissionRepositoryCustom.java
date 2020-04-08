package com.nokchax.escape.mission.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;

import java.util.Optional;

public interface MissionRepositoryCustom {
    /**
     * 원하는 회차의 {@link Mission}을 찾아 리턴.
     * 이때 {@link Mission} 과 함께
     * 참여한 유저 목록 {@link Entry} 도 같이 리턴
     * @param missionId 찾을 미션 id
     */
    Optional<Mission> findMissionAndEntry(Long missionId);

    /**
     * 현재 진행중인 {@link Mission}을 찾아 리턴,
     * 이때 {@link Mission}과 함께
     * 참여한 유저 목록 {@link Entry} 도 리턴
     */
    Optional<Mission> findLatestMissionWithEntry();

    /**
     * 현재 진행중인 {@link Mission}을 찾아 리턴,
     * 이때 {@link Mission}과 함께
     * 참여한 유저 목록 {@link Entry}
     * 뿜만 아니라 유저 정보 {@link com.zum.escape.api.users.domain.User} 도 리턴
     */
    Optional<Mission> findMissionWithEntryAndUser();
}
