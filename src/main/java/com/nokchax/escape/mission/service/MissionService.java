package com.nokchax.escape.mission.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;

    /** * 현재 미션 수행중인 유저 리스트 리턴 */
    public List<Entry> getAllUserInLatestMission() {
        final Mission latestMission = getLatestMission();

        return new ArrayList<>(latestMission.getEntry());
    }

    /** * 현재 미션을 수행중인 사용자 중에서 목표를 달성한 사용자만 리턴 */
    public List<Entry> getAllMissionSuccessUserInLatestMission() {
        final Mission latestMission = getLatestMission();
        final int missionGoalScore = latestMission.getGoalScore();

        return latestMission.getEntry()
                .stream()
                .filter(participant -> participant.isMissionSuccess(missionGoalScore))
                .collect(Collectors.toList());
    }

    /** * 현재 미션을 수행중인 사용자 중에서 목표를 당성하지 못한 사용자만 리턴 */
    public List<Entry> getAllMissioningUserInLatestMission() {
        final Mission latestMission = getLatestMission();
        final int missionGoalScore = latestMission.getGoalScore();

        return latestMission.getEntry()
                .stream()
                .filter(participant -> !participant.isMissionSuccess(missionGoalScore))
                .collect(Collectors.toList());
    }

    /** * 최신 미션 리턴 */
    private Mission getLatestMission() {
        return missionRepository.findLatestMissionWithEntry();
    }
}
