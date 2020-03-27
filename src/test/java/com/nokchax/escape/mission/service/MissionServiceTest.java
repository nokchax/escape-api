package com.nokchax.escape.mission.service;

import com.nokchax.escape.mission.domain.Entry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class MissionServiceTest {
    @Autowired
    private MissionService missionService;

    @Test
    void getAllUserTest() {
        List<Entry> entries = missionService.getAllUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(14);

        entries.forEach(System.out::println);
    }

    @Test
    void getMissionSuccessUserTest() {
        List<Entry> entries = missionService.getAllMissionSuccessUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(4);

        entries.forEach(System.out::println);
    }

    @Test
    void getMissioningUserTest() {
        List<Entry> entries = missionService.getAllMissioningUserInLatestMission();

        assertThat(entries).isNotNull();
        assertThat(entries.size()).isEqualTo(10);

        entries.forEach(System.out::println);
    }
}