package com.nokchax.escape.problem.domain;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "solved_problem")
@Entity
@Getter
@Setter
@NoArgsConstructor
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
