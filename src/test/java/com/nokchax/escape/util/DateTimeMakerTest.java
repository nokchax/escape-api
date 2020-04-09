package com.nokchax.escape.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeMakerTest {

    @Test
    void startOfWeekTest() {
        System.out.println(DateTimeMaker.startOfWeek());
        System.out.println(DateTimeMaker.endOfWeek());

        assertThat(DateTimeMaker.startOfWeek().plusDays(7).minusSeconds(1))
                .isEqualTo(DateTimeMaker.endOfWeek());
    }
}