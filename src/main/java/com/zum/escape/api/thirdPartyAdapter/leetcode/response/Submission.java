package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Submission {
    private String problemTitle;
    private LocalDateTime solvedDate;

    public Submission(String problemTitle, LocalDateTime solvedDate) {
        this.problemTitle = problemTitle;
        this.solvedDate = solvedDate;
    }
}
