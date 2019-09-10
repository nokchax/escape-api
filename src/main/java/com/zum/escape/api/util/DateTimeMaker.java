package com.zum.escape.api.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeMaker {
    public static LocalDateTime getStartOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();
    }

    public static LocalDateTime getEndOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.SUNDAY)
                .atTime(23, 59, 59);
    }
}
