package com.nokchax.escape.leetcode.crawl.page.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class CrawledUserInfo {
    public static CrawledUserInfo NOT_UPDATED_USER_INFO = CrawledUserInfo.builder()
            .userId("")
            .solvedProblemCount(0)
            .solvedProblems(Collections.EMPTY_SET)
            .build();

    private String userId;
    private int solvedProblemCount;
    private Set<ProblemSolveInfo> solvedProblems; // 푼 문제가 DB에 반영되어있는지는 크롤시점에선 알 수 없음

    public boolean isNotUpdate() {
        return solvedProblems.isEmpty();
    }
}
