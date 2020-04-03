package com.nokchax.escape.leetcode.crawl.api.response;

import com.nokchax.escape.problem.domain.Difficulty;
import com.nokchax.escape.problem.domain.Problem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrawledProblemInfo {
    private Long problemId;
    private Long frontendProblemId;
    private String problemTitle;
    private String problemTitleSlug;
    private boolean problemHide;
    private Difficulty difficulty;

    public Problem toProblem() {
        return Problem.builder()
                .id(problemId)
                .viewId(frontendProblemId)
                .title(problemTitle)
                .titleSlug(problemTitleSlug)
                .difficulty(difficulty)
                .hide(problemHide)
                .build();
    }
}
