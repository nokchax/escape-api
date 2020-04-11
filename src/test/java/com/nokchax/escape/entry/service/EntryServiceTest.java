package com.nokchax.escape.entry.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.point.repository.PointRepository;
import com.nokchax.escape.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EntryServiceTest extends ServiceLayerTest {
    @Autowired
    private EntryService entryService;
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PointRepository pointRepository;

    @Test
    @Transactional
    @DisplayName("벌금 부과 테스트")
    void imposeFineTest() {
        List<String> userIds = entryRepository.getLastEntry()
                .stream()
                .map(Entry::getUser)
                .map(User::getId)
                .collect(Collectors.toList());

        // 이전 포인트 map
        List<PointDto> beforeUserPoint = pointRepository.findUserPointByUserId(userIds);
        Map<String, Integer> beforePoint = beforeUserPoint.stream()
                .collect(Collectors.toMap(PointDto::getUserId, PointDto::getPoint));

        // 미션 완료한 사용자 제거
        List<Entry> entries = entryRepository.getLastEntry();

        entries.forEach(System.out::println);
        Map<String, Integer> findPoint = entries.stream()
                .collect(Collectors.toMap(entry -> entry.getUser().getId(), entry -> entry.getMission().getGoalScore() - entry.getScore()));

        beforeQuery();
        entryService.imposeFine();
        afterQuery();

        beforeClear();
        entityManager.flush();
        entityManager.clear();
        afterClear();

        List<PointDto> afterUserPoint = pointRepository.findUserPointByUserId(userIds);

        showResult();
        afterUserPoint.forEach(userPoint -> {
            System.out.println(userPoint.getUserId() + " : " + userPoint.getPoint() + " = " + beforePoint.get(userPoint.getUserId()) + " - " + findPoint.get(userPoint.getUserId()));
            assertThat(userPoint.getPoint()).isEqualTo(
                    beforePoint.get(userPoint.getUserId()) - (findPoint.get(userPoint.getUserId()) > 0 ? findPoint.get(userPoint.getUserId()) : 0)
            );
        });
    }


    @Test
    @Transactional
    @DisplayName("최신 미션 업데이트 테스트")
    void updateEntryInLatestMission() {
        List<String> changeExpectedUserIds = Arrays.asList("nokchax4", "nokchax5", "nokchax11", "nokchax12");

        List<Entry> latestEntry = entryRepository.getLatestEntry();
        Map<String, Entry> beforeEntry = latestEntry.stream()
                .collect(Collectors.toMap(entry -> entry.getUser().getId(), Function.identity()));
        latestEntry.forEach(System.out::println);

        List<User> users = entryRepository.getLatestEntry()
                .stream()
                .map(Entry::getUser)
                .collect(Collectors.toList());

        entityManager.flush();
        entityManager.clear();

        entryService.updateEntryInLatestMission(users);

        entityManager.flush();
        entityManager.clear();

        List<Entry> afterQueryLatestEntry = entryRepository.getLatestEntry();
        Map<String, Entry> afterEntry = afterQueryLatestEntry.stream()
                .collect(Collectors.toMap(entry -> entry.getUser().getId(), Function.identity()));
        afterQueryLatestEntry.forEach(System.out::println);

        changeExpectedUserIds.forEach(userId -> {
            Entry before = beforeEntry.get(userId);
            Entry after = afterEntry.get(userId);
            System.out.println(userId + " : " + before.getScore() + " -> " + after.getScore());
            assertThat(before.getScore()).isNotEqualTo(after.getScore());
        });
    }
}