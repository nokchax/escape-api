package com.zum.escape.api.users.domain;

import com.zum.escape.api.problem.domain.entity.Problem;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.users.dto.UserProblemSolveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private int solvedQuestionCount;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserProblem> solvedProblem = new HashSet<>();

    public User(List<String> args) {
        if(args.size() < 3) {
            throw new IllegalArgumentException("/register leetcodeId pw name");
        }

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
        if(crawledUserInfo.solvedQuestion()) {
            return true;
        }

        return this.solvedQuestionCount > crawledUserInfo.getSolvedQuestionCount();
    }

    public List<UserProblem> updateSolvedProblems(CrawledUserInfo crawledUserInfo) {
        if(crawledUserInfo.getProblems() == null) {
            log.error("problems is null");
            return Collections.emptyList();
        }

        Map<String, LocalDateTime> time = new HashMap<>();

        crawledUserInfo.getSolvedProblems().forEach(
                submission -> time.put(submission.getProblemTitle(), submission.getSolvedDate())
        );

        List<UserProblem> addedProblem = new ArrayList<>();

        for(Problem problem : crawledUserInfo.getProblems()) {
            if(problem == null) {
                log.error("problem is null");
                continue;
            }

            UserProblem userProblem = new UserProblem(this, problem, time.get(problem.getTitle()));
            if(!solvedProblem.contains(userProblem)) {
                solvedProblem.add(userProblem);
                addedProblem.add(userProblem);
            }
        }


        updateSolvedProblemCount();
        return addedProblem;
    }

    public List<UserProblem> updateSolvedProblems(Set<Problem> problems, LocalDateTime solvedTime) {
        if(problems == null || problems.isEmpty()) {
            return Collections.emptyList();
        }

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

        updateSolvedProblemCount();

        return addedProblem;
    }
    public UserProblem updateManually(Problem problem) {
        UserProblem userProblem = new UserProblem(this, problem, LocalDateTime.now());

        solvedProblem.add(userProblem);

        return userProblem;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", solvedQuestionCount=" + solvedQuestionCount +
                '}';
    }


    public boolean isSolvedProblemCountNotCorrect(CrawledUserInfo crawledUserInfo) {
        return this.solvedQuestionCount < crawledUserInfo.getSolvedQuestionCount();
    }
}
