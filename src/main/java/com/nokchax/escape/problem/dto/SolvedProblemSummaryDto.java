package com.nokchax.escape.problem.dto;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.problem.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SolvedProblemSummaryDto {
    private String userId;
    private long missionId;
    private long hardCount;
    private long mediumCount;
    private long easyCount;

    public int evaluateScore() {
        return Difficulty.countToScore(this);
    }

    public Entry toEntry() {
        return new Entry(this);
    }
}
