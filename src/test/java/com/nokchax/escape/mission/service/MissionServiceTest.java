package com.nokchax.escape.mission.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MissionServiceTest extends ServiceLayerTest {
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MissionService missionService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("최신 미션에 포함된 모든 유저 리턴")
    void getAllUserInLatestMissionTest() {
        beforeQuery();
        List<EntryDto> entries = missionService.getAllUserInLatestMission();
        afterQuery();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(14);

        entries.forEach(System.out::println);
    }

    @Test
    @DisplayName("최신 미션을 수행 완료한 유저만 리턴")
    void getMissionSuccessUserTest() {
        beforeQuery();
        List<EntryDto> entries = missionService.getAllMissionSuccessUserInLatestMission();
        afterQuery();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(1);

        entries.forEach(System.out::println);
    }

    @Test
    @DisplayName("최신 미션을 수행중인 유저만 리턴")
    void getMissioningUserTest() {
        beforeQuery();
        List<EntryDto> entries = missionService.getAllMissioningUserInLatestMission();
        afterQuery();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(13);

        entries.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("새 미션 생성 테스트")
    void createNewMissionTest() {
        List<User> users = userRepository.findAll();
        int numOfUsers = users.size();

        beforeQuery();
        missionService.createMission();
        afterQuery();

        beforeClear();
        entityManager.flush();
        entityManager.clear();
        afterClear();

        beforeQuery();
        Mission latestMission = missionService.getLatestMission();
        afterQuery();

        assertThat(latestMission.getId()).isEqualTo(30);
        assertThat(latestMission.getEntry().size()).isEqualTo(numOfUsers);

        showResult();
        System.out.println(latestMission);
        latestMission.getEntry().forEach(System.out::println);
    }

    @Test
    @DisplayName("미션 정보가 없는 푼 문제 리스트에 미션정보 채우기 테스트")
    void fillOutSolvedProblemMissionInfoTest() {
        List<Problem> problems = problemRepository.findAll();
        List<SolvedProblem> solvedProblems = Arrays.asList(
                SolvedProblem.builder().solvedTime(LocalDateTime.of(2020, 1, 1, 0, 0)).problem(problems.get(0)).user(new User("nokchax15")).build(),
                SolvedProblem.builder().solvedTime(LocalDateTime.of(2020, 1, 1, 0, 0)).problem(problems.get(1)).user(new User("nokchax15")).build(),
                SolvedProblem.builder().solvedTime(LocalDateTime.of(2020, 1, 1, 0, 0)).problem(problems.get(2)).user(new User("nokchax15")).build()
        );

        solvedProblems.forEach(solvedProblem -> {
            assertThat(solvedProblem.getMission()).isNull();
        });

        beforeQuery();
        missionService.fillOutSolvedProblemMissionInfo(new HashSet<>(solvedProblems));
        afterQuery();

        solvedProblems.forEach(solvedProblem -> {
            assertThat(solvedProblem.getMission()).isNotNull();
        });
    }

    @Test
    @Transactional
    @DisplayName("최신미션을 가져오는데 DB에 하나도 없는경우")
    void getLatestMissionButNoMissionExist() {
        solvedProblemRepository.deleteAll();
        missionRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() -> missionService.getLatestMission()).isInstanceOf(RuntimeException.class);
    }
}