package com.zum.escape.api.task.domain;

import com.zum.escape.api.users.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
@Entity
@Setter
@Getter
public class TaskDone {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task tasks;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
    private LocalDateTime doneDateTime;
    private int score;
}
