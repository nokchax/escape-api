package com.nokchax.escape.problem.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemSolveHistoryDto extends MessageTemplate {
    private String userId;
    private int totalCount;
    private int hardCount;
    private int mediumCount;
    private int easyCount;

    @QueryProjection
    public ProblemSolveHistoryDto(String userId, int totalCount, int hardCount, int mediumCount, int easyCount) {
        this.userId = userId;
        this.totalCount = totalCount;
        this.hardCount = hardCount;
        this.mediumCount = mediumCount;
        this.easyCount = easyCount;
    }

    private String getShortenId() {
        return userId.length() > 10 ?
                userId.replaceAll("\\d", "") :
                userId;
    }

    @Override
    public String title() {
        return "    ID    | T   H   M   E \n";
    }

    @Override
    public String body() {
        return String.format("%10s|%3d %3d %3d %3d\n", getShortenId(), totalCount, hardCount, mediumCount, easyCount);
    }
}
