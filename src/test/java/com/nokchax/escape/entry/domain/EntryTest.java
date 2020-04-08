package com.nokchax.escape.entry.domain;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EntryTest {
    @Test
    void compareTest() {
        List<Entry> entries = Arrays.asList(
                createEntry(10, 2, 0, 0),
                createEntry(10, 1, 2, 1),
                createEntry(5, 1, 0, 0),
                createEntry(5, 0, 2, 1),
                createEntry(5, 0, 1, 3),
                createEntry(5, 0, 0, 5)
        );

        List<Entry> compareTarget = new ArrayList<>(entries);

        Collections.shuffle(compareTarget);

        Stream<Entry> sorted = compareTarget.stream()
                .sorted();

        AtomicInteger idx = new AtomicInteger();
        entries.forEach(entry -> {
            Entry originalEntry = entries.get(idx.getAndIncrement());
            assertThat(entry.getScore()).isEqualTo(originalEntry.getScore());
            assertThat(entry.getHard()).isEqualTo(originalEntry.getHard());
            assertThat(entry.getMedium()).isEqualTo(originalEntry.getMedium());
            assertThat(entry.getEasy()).isEqualTo(originalEntry.getEasy());
        });

        sorted.forEach(System.out::println);
    }


    private Entry createEntry(int score, int hard, int medium, int easy) {
        return Entry.builder()
                .mission(new Mission(1))
                .user(new User("test"))
                .score(score)
                .hard(hard)
                .medium(medium)
                .easy(easy)
                .build();
    }
}