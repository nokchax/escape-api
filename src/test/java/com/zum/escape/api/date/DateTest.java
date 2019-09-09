package com.zum.escape.api.date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
public class DateTest {
    @Test
    public void startOfWeekTest() {
        LocalDate localDate = LocalDate.now().with(DayOfWeek.MONDAY);
        System.out.println(localDate);
        System.out.println(localDate.atStartOfDay());
        System.out.println(localDate.atTime(LocalTime.MAX));

        LocalDate endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY);
        System.out.println(endOfWeek);
        System.out.println(endOfWeek.atTime(LocalTime.MAX));
    }
}
