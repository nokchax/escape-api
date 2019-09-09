package com.zum.escape.api.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id @GeneratedValue
    private Long id;
    private int goalScore = 5;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    private DurationType durationType;
    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    private List<TaskParticipant> participants = new ArrayList<>();
    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    private List<TaskDone> doneUser = new ArrayList<>();
}
