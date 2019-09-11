package com.zum.escape.api.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Point {
    private int point;
    private LocalDateTime dateTime;
    private Description description;

    public Point(int point, Description description) {
        this.point = point;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public Point(Description description, int point, LocalDateTime imposedTime) {
        this.point = point;
        this.description = description;
        this.dateTime = imposedTime;
    }

    public static Point makeMonthlyPoint(int point, Description description) {
        return new Point(point, description);
    }
}
