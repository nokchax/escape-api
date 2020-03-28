package com.nokchax.escape.problem.dto;

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
    private int hardCount;
    private int mediumCount;
    private int easyCount;

    public int evaluateScore() {
        return Difficulty.countToScore(this);
    }
}
