package com.nokchax.escape.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private int solvedProblemCount;
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private Set<UserProblem> solvedProblem = new HashSet<>();
}
