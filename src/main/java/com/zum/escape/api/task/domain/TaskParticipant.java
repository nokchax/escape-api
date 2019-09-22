package com.zum.escape.api.task.domain;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.dto.PunishedUser;
import com.zum.escape.api.users.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(TaskParticipantId.class)
public class TaskParticipant {
    @Id
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task tasks;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
    private int score;
    private int hard;
    private int medium;
    private int easy;

    public TaskParticipant(Task task, User user) {
        this.tasks = task;
        this.users = user;
        this.score = 0;
        this.hard = 0;
        this.medium = 0;
        this.easy = 0;
    }

    public void updateScore(int score, int hard, int medium, int easy) {
        this.score = score;
        this.hard = hard;
        this.medium = medium;
        this.easy = easy;
    }

    public boolean hasReachedGoal() {
        return this.score >= TaskService.GOAL_SCORE;
    }

    public boolean hasNotReachedGoal() {
        return !hasReachedGoal();
    }

    public UserDto toUserDto() {
        return UserDto.builder()
                .leetcodeId(this.users.getId())
                .score(this.score)
                .hard(this.hard)
                .medium(this.medium)
                .easy(this.easy)
                .build();
    }

    public PunishedUser toPunishedUser() {
        return PunishedUser.builder()
                .user(this.users)
                .lackPoint(TaskService.GOAL_SCORE - this.score)
                .build();
    }
}
