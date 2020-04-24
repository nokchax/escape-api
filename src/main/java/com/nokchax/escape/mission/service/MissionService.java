package com.nokchax.escape.mission.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import com.nokchax.escape.util.DateTimeMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    /** * 현재 미션 수행중인 유저 리스트 리턴 */
    public List<EntryDto> getAllUserInLatestMission() {
        final Mission latestMission = getLatestMission();

        return latestMission.getEntry()
                .stream()
                .map(Entry::toDto)
                .collect(Collectors.toList());
    }

    /** * 현재 미션을 수행중인 사용자 중에서 목표를 달성한 사용자만 리턴 */
    public List<EntryDto> getAllMissionSuccessUserInLatestMission() {
        final Mission latestMission = getLatestMission();
        final int missionGoalScore = latestMission.getGoalScore();

        return latestMission.getEntry()
                .stream()
                .filter(participant -> participant.isMissionComplete(missionGoalScore))
                .map(Entry::toDto)
                .collect(Collectors.toList());
    }

    /** * 현재 미션을 수행중인 사용자 중에서 목표를 달성하지 못한 사용자만 리턴 */
    public List<EntryDto> getAllMissioningUserInLatestMission() {
        final Mission latestMission = getLatestMission();
        final int missionGoalScore = latestMission.getGoalScore();

        return latestMission.getEntry()
                .stream()
                .filter(participant -> !participant.isMissionComplete(missionGoalScore))
                .map(Entry::toDto)
                .collect(Collectors.toList());
    }

    /** * 푼 문제들이 속해있는 mission을 찾아 업데이트 해줌 */
    public void fillOutSolvedProblemMissionInfo(Set<SolvedProblem> solvedProblems) {
        List<Mission> missions = missionRepository.findAll();

        solvedProblems.forEach(
                solvedProblem -> {
                    Mission missionInPeriod = missions.stream()
                            .filter(mission -> mission.isInPeriod(solvedProblem))
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new);
                    solvedProblem.updateMission(missionInPeriod);
                });
    }

    /** * 최신 미션 리턴 */
    public Mission getLatestMission() {
        return missionRepository.findLatestMissionWithEntry()
                .orElseThrow(() -> new RuntimeException("No missions in db"));

    }

    /** * 이전 미션 정리 및 새 미션 생성 */
    public void createMission() {
        Mission newMission = Mission.builder()
                .startDateTime(DateTimeMaker.startOfWeek())
                .endDateTime(DateTimeMaker.endOfWeek())
                .build();
        List<User> users = userRepository.findAll();

        newMission.entry(users);

        missionRepository.save(newMission);
    }

    public void updateMission() {
        List<User> users = userRepository.findUsersNotInLatestMission();

        if(CollectionUtils.isEmpty(users)) {
            throw new UserNotFoundException("현재 미션을 수행하지 않는 유저가 존재하지 않습니다.");
        }

        Mission latestMission = missionRepository.findLatestMissionWithEntry()
                .<IllegalArgumentException>orElseThrow(() -> {throw new IllegalArgumentException("no missions");});

        latestMission.updateEntry(users);
    }
}
