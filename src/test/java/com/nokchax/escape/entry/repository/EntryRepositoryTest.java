package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.dto.EntryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class EntryRepositoryTest {
    @Autowired
    private EntryRepository entryRepository;

    @Test
    void getLastMissionTest() {
        List<EntryDto> allUserIncompleteLastMission = entryRepository.findAllUserIncompleteLastMission();

        assertThat(allUserIncompleteLastMission.get(0).getMissionId()).isEqualTo(28);

        allUserIncompleteLastMission.forEach(System.out::println);
    }

}