package com.nokchax.escape.entry.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
class EntryServiceTest {
    @Autowired
    private EntryService entryService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MissionRepository missionRepository;

    @Test
    void updateEntryTest() {
        Mission latestMission = missionRepository.findLatestMissionWithEntry();
        latestMission.getEntry()
                .forEach(System.out::println);

        System.out.println("=========================================================Before update");
        List<Entry> entries = entryService.updateLatestEntry();
        entries.forEach(System.out::println);
        System.out.println("=========================================================After update");
        entityManager.clear();

        latestMission = missionRepository.findLatestMissionWithEntry();
        latestMission.getEntry()
                .forEach(System.out::println);
    }

}