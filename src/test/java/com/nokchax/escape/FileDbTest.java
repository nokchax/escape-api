package com.nokchax.escape;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("filedb")
public class FileDbTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;
    @Autowired
    private MissionRepository missionRepository;

    @Test
    void fileCreationTest() {
        solvedProblemRepository.findAll().forEach(System.out::println);
    }

    @Test
    void queryDslTest() {
        Mission missionAndEntry = missionRepository.findMissionAndEntry(10L);
        System.out.println(missionAndEntry);
        missionAndEntry.getEntry().forEach(System.out::println);
    }

    @Test
    void jpaDataTest() {
        Optional<Mission> byId = missionRepository.findById(10L);

        byId.map(Mission::getEntry)
                .get()
                .forEach(System.out::println);
    }
}
