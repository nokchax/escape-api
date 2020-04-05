package com.nokchax.escape.entry.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.point.repository.PointRepository;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;
    private final MissionRepository missionRepository;
    private final SolvedProblemRepository solvedProblemRepository;
    private final PointRepository pointRepository;

    @Transactional
    public List<Entry> updateLatestEntry() {
        List<SolvedProblemSummaryDto> solvedProblemSummaries = solvedProblemRepository.getSolvedProblemOfLatestMissionUser();

        List<Entry> entries = solvedProblemSummaries.stream()
                .map(SolvedProblemSummaryDto::toEntry)
                .collect(Collectors.toList());

        return entryRepository.saveAll(entries);
    }

    public List<EntryDto> findAllUserInLatestMission(List<User> users) {
        return entryRepository.findAllUserInLatestMission(
                users.stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void imposeFine() {
        List<Entry> penaltyUsers = entryRepository.findAllUserIncompleteLastMission();

        List<Point> finePoints = penaltyUsers.stream()
                .map(Entry::imposeFine)
                .collect(Collectors.toList());

        pointRepository.saveAll(finePoints);
    }
}
