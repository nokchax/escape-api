package com.nokchax.escape.point.domain;

import com.nokchax.escape.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@IdClass(PointId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    private LocalDateTime dateTime;
    private int point;
    private String description;
}
