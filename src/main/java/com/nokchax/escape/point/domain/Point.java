package com.nokchax.escape.point.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Point {
    private int point;
    private LocalDateTime dateTime;
    private String description;
}
