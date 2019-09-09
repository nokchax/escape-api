package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.Submission;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserProblem> solvedProblem = new HashSet<>();

    public boolean checkSolveQuestion(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.solvedQuestion())
            return false;

        return this.solvedQuestionCount > crawledUserInfo.getSolvedQuestionCount();
    }

    public void setSolvedProblem(Set<UserProblem> solved) {
        this.solvedProblem = solved;
    }

    public List<Problem> updateSolvedProblems(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.getProblems() == null) {
            System.out.println("NULL");
            return Collections.emptyList();
        }
        Map<String, LocalDateTime> time = new HashMap<>();

        crawledUserInfo.getSolvedProblems().forEach(
                submission -> time.put(submission.getProblemTitle(), submission.getSolvedDate())
        );

        List<Problem> addedProblem = new ArrayList<>();

        for(Problem problem : crawledUserInfo.getProblems()) {
            UserProblem userProblem = new UserProblem(this, problem, time.get(problem.getTitle()));
            if(!solvedProblem.contains(userProblem)) {
                solvedProblem.add(userProblem);
                addedProblem.add(problem);
            }
        }

        return addedProblem;
    }
}
