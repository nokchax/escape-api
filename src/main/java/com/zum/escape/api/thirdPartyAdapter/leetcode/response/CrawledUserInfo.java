package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawledUserInfo {
    public static final CrawledUserInfo NOT_UPDATED = CrawledUserInfo.builder().build();
    private String userId;
    private int solvedQuestionCount;
    private Set<String> solvedProblems = Collections.emptySet();
    private Set<Problem> problems = Collections.emptySet();

    public boolean solvedQuestion() {
        return !solvedProblems.isEmpty();
    }

    public User toUser() {
        return User.builder()
                .solvedQuestionCount(this.solvedQuestionCount)
                .build();
    }
}
