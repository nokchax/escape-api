package com.nokchax.escape.task.domain;

import com.zum.escape.api.task.domain.Duration;
import com.zum.escape.api.task.domain.TaskParticipant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id @GeneratedValue
    private Long id;
    private int goalScore = 5;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
/*
    @Enumerated(EnumType.STRING)
    private Duration durationType;
    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    private List<TaskParticipant> participants = new ArrayList<>();
*/
}
/**
 * task 개념 불필요..?
 * - 미션은 최소 하루 단위
 * - 미션은 목표(채워야 할 포인트)가 있다.
 * - 미션 참가자가 있다. (참가하지 않는 유저도 있다)
 * - 미션은 시작과 끝이 존재한다.
 * - 미션 수행 기간이 존재한다.
 *
 * 문제를 푼 시점과 미션의 지속 기간(하루면 하루, 일주일이면 일주일 등...)을 가지고 고유한 task 값을 구할 수 없을까?
 * 
 */
