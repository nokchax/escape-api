package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.users.dto.UserProblemSolveDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private int solvedQuestionCount;
    @OneToMany(mappedBy = "user")
    private Set<UserProblem> solvedProblem = new HashSet<>();

    public User(List<String> args) {
        if(args.size() < 3)
            throw new IllegalArgumentException("/register leetcodeId pw name");

        this.id = args.get(0);
        this.password = args.get(1);
        this.name = args.get(2);
        this.solvedProblem = new HashSet<>();
    }

    public User(String password, String name, String id) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.solvedProblem = new HashSet<>();
    }

    public boolean checkSolveQuestion(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.solvedQuestion())
            return true;

        return this.solvedQuestionCount > crawledUserInfo.getSolvedQuestionCount();
    }

    public List<UserProblem> updateSolvedProblems(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.getProblems() == null) {
            System.out.println("NULL");
            return Collections.emptyList();
        }
        System.out.println(crawledUserInfo.getProblems());

        Map<String, LocalDateTime> time = new HashMap<>();

        crawledUserInfo.getSolvedProblems().forEach(
                submission -> time.put(submission.getProblemTitle(), submission.getSolvedDate())
        );

        List<UserProblem> addedProblem = new ArrayList<>();

        for(Problem problem : crawledUserInfo.getProblems()) {
            UserProblem userProblem = new UserProblem(this, problem, time.get(problem.getTitle()));
            if(!solvedProblem.contains(userProblem)) {
                System.out.println(userProblem);
                solvedProblem.add(userProblem);
                addedProblem.add(userProblem);
            }
        }


        return addedProblem;
    }

    public List<UserProblem> updateSolvedProblems(Set<Problem> problems, LocalDateTime solvedTime) {
        if(problems == null || problems.isEmpty())
            return Collections.emptyList();

        List<UserProblem> addedProblem = new ArrayList<>();

        for(Problem problem : problems) {
            UserProblem userProblem = new UserProblem(this, problem, solvedTime);

            boolean add = true;
            for(UserProblem solvedBefore : solvedProblem) {
                if (solvedBefore.getProblem().getId().equals(problem.getId())) {
                    add = false;
                    break;
                }
            }
            if(add) {
                solvedProblem.add(userProblem);
                addedProblem.add(userProblem);
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

    public UserProblemSolveDto toUserProblemSolveDto() {
        return UserProblemSolveDto.builder()
                .leetcodeId(this.id)
                .build();
    }

    public void updateSolvedProblemCount() {
        this.solvedQuestionCount = solvedProblem.size();
    }
}
