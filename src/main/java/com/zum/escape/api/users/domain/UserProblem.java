package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
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
}
