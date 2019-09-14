package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
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
    @Id
    private String email;
    private String password;
    private String name;
    @Column(unique = true)
    private String leetcodeName;
    private int solvedQuestionCount;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserProblem> solvedProblem = new HashSet<>();

    public User(List<String> args) {
        if(args.size() < 4)
            throw new IllegalArgumentException("/register email pw name leetcodeId");

        this.email = args.get(0);
        this.password = args.get(1);
        this.name = args.get(2);
        this.leetcodeName = args.get(3);
        this.solvedProblem = new HashSet<>();
    }

    public boolean checkSolveQuestion(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.solvedQuestion())
            return false;

        return this.solvedQuestionCount > crawledUserInfo.getSolvedQuestionCount();
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

    public UserHistory getPoints(Point point) {
        return UserHistory.builder()
                .user(this)
                .point(point.getPoint())
                .dateTime(point.getDateTime())
                .description(point.getDescription())
                .build();
    }
}
