package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.zum.escape.api.problem.domain.entity.Problem;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawledUserInfo {
    public static final CrawledUserInfo NOT_UPDATED = CrawledUserInfo.builder()
            .solvedQuestionCount(0)
            .solvedProblems(new HashSet<>())
            .problems(new HashSet<>())
            .build();

    private String userId;
    private int solvedQuestionCount;
    private Set<Submission> solvedProblems = new HashSet<>();
    private Set<Problem> problems = new HashSet<>();

    public boolean solvedQuestion() {
        return !solvedProblems.isEmpty();
    }

    @Override
    public String toString() {
        return "CrawledUserInfo{" +
                "userId='" + userId + '\'' +
                ", solvedQuestionCount=" + solvedQuestionCount +
                ", solvedProblems=" + solvedProblems +
                '}';
    }
}
