package com.nokchax.escape.problem.dto;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.problem.domain.Difficulty;
import com.nokchax.escape.user.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolvedProblemSummaryDto {
    private String userId;
    private long missionId;
    private int hardCount;
    private int mediumCount;
    private int easyCount;

    @QueryProjection
    public SolvedProblemSummaryDto(String userId, long missionId, int hardCount, int mediumCount, int easyCount) {
        this.userId = userId;
        this.missionId = missionId;
        this.hardCount = hardCount;
        this.mediumCount = mediumCount;
        this.easyCount = easyCount;
    }

    public int evaluateScore() {
        return Difficulty.countToScore(this);
    }

    public Entry toEntry() {
        return Entry.builder()
                .mission(new Mission(missionId))
                .user(new User(userId))
                .score(evaluateScore())
                .hard(hardCount)
                .medium(mediumCount)
                .easy(easyCount)
                .build();
    }
}
