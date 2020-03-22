package com.nokchax.escape.mission.domain;

import com.nokchax.escape.user.domain.User;
import com.zum.escape.api.task.domain.TaskParticipantId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(TaskParticipantId.class)
public class Entry {
    @Id
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Mission mission;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
    private int score;
    private int hard;
    private int medium;
    private int easy;
}
