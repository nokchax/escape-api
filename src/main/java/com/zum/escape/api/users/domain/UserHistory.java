package com.zum.escape.api.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
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

    @Override
    public String toString() {
        return user + ": " + point + "\n" + dateTime + " / " + description;
    }
}
