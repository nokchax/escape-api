package com.nokchax.escape.leetcode.crawl.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.problem.domain.Difficulty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemInfo {
    @JsonProperty("stat")
    private Detail detail;
    private String status;
    private Level difficulty; // difficulty 1 easy, 2 medium, 3 hard

    public boolean isSolvedProblem() {
        return "ac".equalsIgnoreCase(status);
    }

    public ProblemSolveInfo toProblemSolveInfo() {
        return ProblemSolveInfo.builder()
                .problemTitle(detail.getProblemTitle())
                .solvedDate(LocalDateTime.now()) //우선 크롤 시간으로 수정한 다음 나중에 덮어쓰는 식으로 해야할 듯
                .build();
    }

    public CrawledProblemInfo toCrawledProblemInfo() {
        return CrawledProblemInfo.builder()
                .problemId(detail.getProblemId())
                .problemHide(detail.isProblemHide())
                .problemTitle(detail.getProblemTitle())
                .problemTitleSlug(detail.getProblemTitleSlug())
                .frontendProblemId(detail.getFrontendProblemId())
                .difficulty(Difficulty.ofLevel(difficulty.getLevel()))
                .build();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Level {
        private int level;
    }
}
