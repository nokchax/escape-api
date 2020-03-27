package com.nokchax.escape.entry.service;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;
    private final MissionRepository missionRepository;

    int updateLatestEntry() {

        return -1;
    }

}
