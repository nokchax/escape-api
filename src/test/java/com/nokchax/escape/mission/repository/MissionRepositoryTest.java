package com.nokchax.escape.mission.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MissionRepositoryTest extends JpaTest {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("미션 번호를 넘겨서 미션과 엔트리 정보를 찾아오는지 테스트")
    void findMissionAdnEntryTest() {
        Mission mission = missionRepository.findMissionAndEntry(2L)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(mission.getId()).isEqualTo(2);
        assertThat(mission.getEntry().size()).isEqualTo(10);

        mission.getEntry().forEach(System.out::println);
    }

    @ValueSource(longs = {-1L, 1234567890L})
    @ParameterizedTest
    @DisplayName("존재하지 않는 미션 번호를 넘겼을때 익셉션이 발생하는지 테스트")
    void invalidMissionIdTest(long missionId) {
        assertThatThrownBy(() -> missionRepository.findMissionAndEntry(missionId).orElseThrow(IllegalArgumentException::new))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("미션이 하나도 존재 하지 않을때 예외가 발생하는지 테스트")
    void noMissionExceptionTest() {
        /* @DataJpaTest는 테스트가 끝날 때마다 자동으로 테스트에 사용한 데이터를 롤백 */
        solvedProblemRepository.deleteAll();
        entryRepository.deleteAll();
        missionRepository.deleteAll();

        assertThatThrownBy(() -> missionRepository.findLatestMissionWithEntry().orElseThrow(NullPointerException::new))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("가장 최신 미션과 그 미션의 엔트리 정보를 가져오는지 테스트")
    void findLatestMissionTest() {
        Mission latestMission = missionRepository.findLatestMissionWithEntry()
                .orElseThrow(NullPointerException::new);

        assertThat(latestMission.getId()).isEqualTo(29);
        assertThat(latestMission.getEntry().size()).isEqualTo(14);
    }

    @Test
    @DisplayName("가장 최신 미션과 그 미션의 엔트리 정보, 유저 정보를 함께 가져오는지 테스트")
    void findMissionWithEntryAndUserTest() {
        Mission missionWithEntryAndUser = missionRepository.findMissionWithEntryAndUser()
                .orElseThrow(NullPointerException::new);

        assertThat(missionWithEntryAndUser).isNotNull();
        assertThat(missionWithEntryAndUser.getId()).isEqualTo(29);
        assertThat(missionWithEntryAndUser.getEntry().size()).isEqualTo(14);
        missionWithEntryAndUser.getEntry()
                .stream()
                .map(Entry::getUser)
                .forEach(user -> assertThat(user).isNotNull());

        log.info("Mission info");
        System.out.println(missionWithEntryAndUser);

        log.info("Entry info");
        missionWithEntryAndUser.getEntry()
                .forEach(System.out::println);

        log.info("User info");
        missionWithEntryAndUser.getEntry()
                .stream()
                .map(Entry::getUser)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("가장 최신 미션과 그 미션의 엔트리와 유저를 가져오는데, 엔트리가 없다면?")
    void findMissionWithEntryAndUserTest_ButUserNotExist() {
        /* 미션이라도 가져와야 함 */
        Mission newMission = new Mission(30);
        missionRepository.save(newMission);

        entityManager.flush();
        entityManager.clear();

        Mission missionWithEntryAndUser = missionRepository.findMissionWithEntryAndUser()
                .orElseThrow(NullPointerException::new);

        assertThat(missionWithEntryAndUser).isNotNull();
        assertThat(missionWithEntryAndUser.getId()).isEqualTo(30);
        assertThat(missionWithEntryAndUser.getEntry().size()).isZero();

        log.info("Mission info");
        System.out.println(missionWithEntryAndUser);

        log.info("Entry info");
        missionWithEntryAndUser.getEntry()
                .forEach(System.out::println);
    }

}