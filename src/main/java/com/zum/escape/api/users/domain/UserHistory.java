package com.zum.escape.api.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@IdClass(UserHistoryId.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    private LocalDateTime dateTime;
    private int point;
    private Description description;
}
