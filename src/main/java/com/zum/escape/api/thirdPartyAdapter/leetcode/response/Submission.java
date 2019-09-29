package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class Submission {
    private String problemTitle;
    private LocalDateTime solvedDate;

    public Submission(String problemTitle, LocalDateTime solvedDate) {
        this.problemTitle = problemTitle;
        this.solvedDate = solvedDate;
    }
}
