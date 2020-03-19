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
/**
 * task 개념 불필요..?
 * - 미션은 최소 하루 단위
 *
 * 문제를 푼 시점과 미션의 지속 기간(하루면 하루, 일주일이면 일주일 등...)을 가지고 고유한 task 값을 구할 수 없을까?
 * 
 */
