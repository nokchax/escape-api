package com.nokchax.escape.entry.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.repository.EntryRepository;
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
    private final SolvedProblemRepository solvedProblemRepository;
    private final PointRepository pointRepository;

    public List<Entry> updateLatestEntry(List<User> users) {
        List<SolvedProblemSummaryDto> solvedProblemSummaries = solvedProblemRepository.findSolvedProblemOfLatestMissionByUserId(
                users.stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );

        List<Entry> entries = solvedProblemSummaries.stream()
                .map(SolvedProblemSummaryDto::toEntry)
                .collect(Collectors.toList());

        return entryRepository.saveAll(entries);
    }

    public List<EntryDto> findAllUserInLatestMission(List<User> users) {
        return entryRepository.findUsersInLatestMissionByUserId(
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

    public List<EntryDto> updateEntryInLatestMission(List<User> users) {
        List<Entry> entries = updateLatestEntry(users);

        return entries.stream()
                .map(Entry::toDto)
                .collect(Collectors.toList());
    }
}
