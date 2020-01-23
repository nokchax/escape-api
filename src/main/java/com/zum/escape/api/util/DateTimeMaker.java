package com.zum.escape.api.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeMaker {
    public static LocalDateTime startOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();
    }

    public static LocalDateTime endOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.SUNDAY)
                .atTime(23, 59, 59);
    }

    public static LocalDateTime startOfLastWeek() {
        return startOfWeek()
                .minusWeeks(1);
    }

    public static LocalDateTime yesterday() {
        return LocalDateTime.now()
                .minusDays(1)
                .with(LocalTime.of(23,59,59));
    }
}
