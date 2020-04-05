package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
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
        List<Entry> allUserIncompleteLastMission = entryRepository.findAllUserIncompleteLastMission();

        assertThat(allUserIncompleteLastMission.size()).isEqualTo(1);
        assertThat(allUserIncompleteLastMission.get(0).getMission().getId()).isEqualTo(28);

        allUserIncompleteLastMission.forEach(System.out::println);

        Entry entry = allUserIncompleteLastMission.get(0);
        assertThat(entry.imposeFine().getPoint()).isEqualTo(-5);
    }
}