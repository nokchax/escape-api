package com.nokchax.escape.problem.domain;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "solved_problem")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SolvedProblemId.class)
public class SolvedProblem {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private LocalDateTime solvedTime;
    private LocalDateTime updatedTime;

    public SolvedProblem(User user, Problem problem, CrawledUserInfo crawledUserInfo) {
        this.user = user;
        this.problem = problem;

        ProblemSolveInfo problemSolveInfo = crawledUserInfo.getSolvedProblems()
                .stream()
                .filter(solvedProblem -> solvedProblem.isSameTitle(problem.getTitle()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Problem is not exists"));

        this.solvedTime = problemSolveInfo.getSolvedDate();
        this.updatedTime = LocalDateTime.now();
    }

    public void updateMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return "SolvedProblem{" +
                "userId=" + user.getId() + //lazy로 했을때 id값만 가져오면 추가적인 query를 수행 안 함
                ", problemId=" + problem.getId() +
                ", missionId=" + mission.getId() +
                ", solvedTime=" + solvedTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
