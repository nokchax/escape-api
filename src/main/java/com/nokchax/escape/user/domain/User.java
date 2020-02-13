package com.nokchax.escape.user.domain;

import com.zum.escape.api.users.domain.UserProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private int solvedQuestionCount;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserProblem> solvedProblem = new HashSet<>();
}
