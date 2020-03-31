package com.nokchax.escape.leetcode.crawl.page.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CrawledUserInfo {
    private String userId;
    private int solvedProblemCount;
    private Set<ProblemSolveInfo> solvedProblems;

    private CrawledUserInfo() {}
}
