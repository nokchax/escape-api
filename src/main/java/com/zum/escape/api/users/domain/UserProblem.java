package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.dto.UserProblemSolveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(UserProblemId.class)
public class UserProblem {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private LocalDateTime solvedTime;

    public UserProblem(User user, Problem problem, LocalDateTime solvedTime) {
        this.user = user;
        this.problem = problem;
        this.solvedTime = solvedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProblem that = (UserProblem) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(problem, that.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, problem);
    }
}
