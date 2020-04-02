package com.nokchax.escape.leetcode.crawl.page.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CrawledUserInfo {
    private String userId;
    private int solvedProblemCount;
    private Set<ProblemSolveInfo> solvedProblems; // 푼 문제가 DB에 반영되어있는지는 크롤시점에선 알 수 없음
}
