package com.nokchax.escape.user.domain;

import com.nokchax.escape.problem.domain.SolvedProblem;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
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
    private String telegramId;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SolvedProblem> solvedProblem = new HashSet<>();

    public User(String id, String password, String name) {
        checkInput(id, password, name);

        this.id = id;
        this.password = password;
        this.name = name;
    }

    private void checkInput(String... inputs) {
        Arrays.stream(inputs)
                .forEach(input -> {
                    if(StringUtils.isEmpty(input)) {
                        throw new IllegalArgumentException("Input is null or empty");
                    }
                });
    }
}
