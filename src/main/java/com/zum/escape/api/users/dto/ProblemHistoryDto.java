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
        return userId + ": T[" + totalCount + "], H[" + hardCount + "], M[" + mediumCount + "], E[" + easyCount + "]";
    }
}
