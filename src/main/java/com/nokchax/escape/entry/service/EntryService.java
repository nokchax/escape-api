package com.nokchax.escape.entry.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
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

    @Transactional
    public List<Entry> updateLatestEntry() {
        List<SolvedProblemSummaryDto> solvedProblemSummaries = solvedProblemRepository.getSolvedProblemOfLatestMissionUser();

        List<Entry> entries = solvedProblemSummaries.stream()
                .map(SolvedProblemSummaryDto::toEntry)
                .collect(Collectors.toList());

        return entryRepository.saveAll(entries);
    }

}
