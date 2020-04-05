package com.nokchax.escape.mission.service;

import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@Import(MissionService.class)
@ActiveProfiles("dev")
class MissionServiceTest {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MissionService missionService;
    @Autowired
    private EntityManager entityManager;

    @Test
    void getAllUserTest() {
        List<EntryDto> entries = missionService.getAllUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(14);

        entries.forEach(System.out::println);
    }

    @Test
    void getMissionSuccessUserTest() {
        List<EntryDto> entries = missionService.getAllMissionSuccessUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(0);

        entries.forEach(System.out::println);
    }

    @Test
    void getMissioningUserTest() {
        List<EntryDto> entries = missionService.getAllMissioningUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(14);

        entries.forEach(System.out::println);
    }

    @Test
    void createNewMissionTest() {
        System.out.println("Before create mission");
        missionService.createMission();
        System.out.println("After create mission");

        entityManager.flush();
        entityManager.clear();

        System.out.println("Before find latest mission");
        Mission latestMission = missionService.getLatestMission();
        System.out.println("After find latest mission");

        assertThat(latestMission.getId()).isEqualTo(30);

        System.out.println(latestMission);
    }
}