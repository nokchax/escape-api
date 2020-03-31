package com.nokchax.escape.leetcode.crawl.page.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSolveInfo {
    private String problemTitle;
    private LocalDateTime solvedDate;
}
