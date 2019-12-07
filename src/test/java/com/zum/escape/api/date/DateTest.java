package com.zum.escape.api.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
public class DateTest {
    @Test
    public void startOfWeekTest() {
        LocalDate localDate = LocalDate.now().with(DayOfWeek.MONDAY);
        log.info("{}", localDate);
        log.info("{}", localDate.atStartOfDay());
        log.info("{}", localDate.atTime(LocalTime.MAX));

        LocalDate endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY);
        log.info("{}", endOfWeek);
        log.info("{}", endOfWeek.atTime(LocalTime.MAX));
    }
}
