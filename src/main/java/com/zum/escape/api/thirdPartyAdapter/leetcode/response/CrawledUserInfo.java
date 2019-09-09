package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawledUserInfo {
    public static final CrawledUserInfo NOT_UPDATED = CrawledUserInfo.builder().solvedQuestionCount(0).build();
    private String userId;
    private int solvedQuestionCount;
    private Set<Submission> solvedProblems = new HashSet<>();
    private Set<Problem> problems = new HashSet<>();

    public boolean solvedQuestion() {
        return !solvedProblems.isEmpty();
    }

    public User toUser() {
        return User.builder()
                .userId(this.userId)
                .solvedQuestionCount(this.solvedQuestionCount)
                .solvedProblem(new HashSet<>())
                .build();
    }
}
