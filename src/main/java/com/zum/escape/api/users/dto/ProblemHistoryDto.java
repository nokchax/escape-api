package com.zum.escape.api.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemHistoryDto extends Message {
    private String userId;
    private int totalCount;
    private int hardCount;
    private int mediumCount;
    private int easyCount;

    @Override
    public String toMessage() {
        return String.format("| %12s | %3d | %3d | %3d | %3d |", userId, totalCount, hardCount, mediumCount, easyCount);
    }
}
