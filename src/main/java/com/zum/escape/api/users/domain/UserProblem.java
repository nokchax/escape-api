package com.zum.escape.api.users.domain;

import com.zum.escape.api.domain.entity.Problem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate localDate;

    public UserProblem(User user, Problem problem) {
        this.user = user;
        this.problem = problem;
    }
}
