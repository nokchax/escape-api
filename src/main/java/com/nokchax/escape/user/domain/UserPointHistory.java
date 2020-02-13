package com.nokchax.escape.user.domain;

import com.zum.escape.api.users.domain.Description;
import com.zum.escape.api.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "user_history")
@Builder
@Getter
@IdClass(UserPointHistoryId.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserPointHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    private LocalDateTime dateTime;
    private int point;
    private String description;
}
