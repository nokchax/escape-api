package com.nokchax.escape.util;

import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.problem.domain.Difficulty;

public class ObjectMaker {
    public static CrawledProblemInfo crawledProblemInfoMaker(long frontendProblemId, String title, String titleSlug, long problemId, Difficulty difficulty) {
        return CrawledProblemInfo.builder()
                .frontendProblemId(frontendProblemId)
                .problemTitle(title)
                .problemTitleSlug(titleSlug)
                .problemId(problemId)
                .problemHide(false)
                .difficulty(difficulty)
                .build();
    }
}
