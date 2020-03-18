package com.nokchax.escape.task.domain;

import com.zum.escape.api.users.domain.UserProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private String id;
    private String password;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int solvedQuestionCount;
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private Set<UserProblem> solvedProblem = new HashSet<>();
}
