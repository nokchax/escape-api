package com.zum.escape.api.task.domain;

import com.zum.escape.api.users.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
@Entity
@Getter
@Setter
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
}
