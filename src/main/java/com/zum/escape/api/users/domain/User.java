package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue
    private Long id;
    private String userId;
    private int solvedQuestionCount;
    @ManyToMany
    @JoinTable(name = "solved_problem")
    private Set<Problem> solvedProblems = new HashSet<>();

    public boolean checkSolveQuestion(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.solvedQuestion())
            return false;

        return this.solvedQuestionCount > crawledUserInfo.getSolvedQuestionCount();
    }
}
