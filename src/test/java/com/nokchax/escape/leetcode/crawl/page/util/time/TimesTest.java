package com.nokchax.escape.leetcode.crawl.page.util.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class TimesTest {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void calculateTimeTest() {
        String time = "1 week, 3 days, 7 hours ago";

        Times times = Times.of(time);

        LocalDateTime calculatedTime = times.calculate();

        LocalDateTime standardTime = LocalDateTime.now()
                .minusDays(10)
                .minusHours(7);

        System.out.println("calculated time : " + FORMAT.format(calculatedTime));
        System.out.println("expected time : " + FORMAT.format(standardTime));
        assertThat(FORMAT.format(calculatedTime)).isEqualTo(FORMAT.format(standardTime));
    }

}