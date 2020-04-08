package com.nokchax.escape.entry.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.dto.EntryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EntryRepositoryTest extends JpaTest {
    @Autowired
    private EntryRepository entryRepository;

    @Test
    @DisplayName("지난 미션에 참가한 사용자 중 미션에 실패한 모든 엔트리 정보 찾기")
    void getIncompleteEntriesLastMissionTest() {
        List<Entry> allUserIncompleteLastMission = entryRepository.findAllUserIncompleteLastMission();

        assertThat(allUserIncompleteLastMission.size()).isEqualTo(1);
        assertThat(allUserIncompleteLastMission.get(0).getMission().getId()).isEqualTo(28);

        allUserIncompleteLastMission.forEach(System.out::println);
    }

    @MethodSource
    @ParameterizedTest
    @DisplayName("최신 미션을 수행중인 사용자 중에서 원하는 사용자만 찾기")
    void getLatestMissionByUserIdTest(List<String> userIds, int numOfUsers) {
        List<EntryDto> usersInLatestMission = entryRepository.findUsersInLatestMissionByUserId(userIds);

        assertThat(usersInLatestMission.size()).isEqualTo(numOfUsers);
    }

    private static Stream<Arguments> getLatestMissionByUserIdTest() {
        return Stream.of(
                Arguments.of(Collections.singletonList("nokchax1"), 1),
                Arguments.of(Arrays.asList("nokchax1", "nokchax2", "nokchax3"), 3),
                Arguments.of(Collections.EMPTY_LIST, 0)
        );
    }

    @Test
    @DisplayName("최신 미션정보 가져오기")
    void getLatestMissionTest() {
        List<Entry> latestEntry = entryRepository.getLatestEntry();

        assertThat(latestEntry.size()).isEqualTo(14);
        assertThat(latestEntry.get(0).getMission().getId()).isEqualTo(29);
    }
}