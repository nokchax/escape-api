package com.nokchax.escape.user.domain;

import com.nokchax.escape.problem.domain.SolvedProblem;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@ToString
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private int solvedProblemCount;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<SolvedProblem> solvedProblem = new HashSet<>();
}
