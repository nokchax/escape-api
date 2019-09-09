package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class UserProblem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private LocalDateTime solvedDateTime;

    public UserProblem(User user, Problem problem, LocalDateTime solvedDateTime) {
        this.user = user;
        this.problem = problem;
        this.solvedDateTime = solvedDateTime;
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
