package com.zum.escape.api.task.domain;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TaskParticipant {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task tasks;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
    private int score;

    public TaskParticipant(Task task, User user) {
        this.tasks = task;
        this.users = user;
        this.score = 0;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public boolean hasReachedGoal() {
        return this.score >= TaskService.GOAL_SCORE;
    }

    public boolean hasNotReachedGoal() {
        return !hasReachedGoal();
    }

    public UserDto toUserDto() {
        return UserDto.builder()
                .leetcodeId(this.users.getUserId())
                .score(this.score)
                .build();
    }
}
