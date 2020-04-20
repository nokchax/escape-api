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
    @Enumerated(EnumType.STRING)
    private Description description;

    public Point(User user, int point) {
        this.user = user;
        this.point = point;
        this.dateTime = LocalDateTime.now();
        this.description = Description.PROVIDE_POINT;
    }

    @Getter
    public enum Description {
        FINES("벌금 부과"),
        PAY_FINES("벌금 납부"),
        PROVIDE_POINT("포인트 지급"),
        USE_POINT("포인트 사용");

        private String desc;

        Description(String desc) {
            this.desc = desc;
        }
    }
}
