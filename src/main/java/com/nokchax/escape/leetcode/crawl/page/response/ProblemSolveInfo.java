package com.nokchax.escape.leetcode.crawl.page.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSolveInfo {
    private String problemTitle;
    private LocalDateTime solvedDate;

    public boolean isSameTitle(String title) {
        return problemTitle.equals(title);
    }
}
