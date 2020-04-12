package com.nokchax.escape.leetcode.crawl.page.util.time;

import com.nokchax.escape.exception.InvalidTimeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTypeTest {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ParameterizedTest
    @ValueSource(strings = {"monht", "dayys", "time"})
    @DisplayName("올바른 시간 타입이 아닌 경우")
    void typeTest(String type) {
        assertThatThrownBy(() -> TimeType.of(type)).isInstanceOf(InvalidTimeType.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("시간 타입이 null 이거나 빈값일때")
    void ofTest(String type) {
        assertThatThrownBy(() -> TimeType.of(type)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("시간 문자열에 따라 올바른 시간 타입을 리턴하는지")
    void getTypeTest(String stringType, TimeType expectedTimeType) {
        assertThat(TimeType.of(stringType)).isEqualTo(expectedTimeType);
    }

    private static Stream<Arguments> getTypeTest() {
        return Stream.of(
                Arguments.of("minute", TimeType.MINUTE),
                Arguments.of("minutes", TimeType.MINUTES),
                Arguments.of("hour", TimeType.HOUR),
                Arguments.of("hours", TimeType.HOURS),
                Arguments.of("day", TimeType.DAY),
                Arguments.of("days", TimeType.DAYS),
                Arguments.of("week", TimeType.WEEK),
                Arguments.of("weeks", TimeType.WEEKS),
                Arguments.of("month", TimeType.MONTH),
                Arguments.of("months", TimeType.MONTHS)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("타입에 따라 올바른 시간을 계산해서 리턴하는지")
    void calculateTest(TimeType timeType, LocalDateTime expectedTime, LocalDateTime now) {
        assertThat(FORMAT.format(timeType.minus(now, 1))).isEqualTo(FORMAT.format(expectedTime));
    }

    private static Stream<Arguments> calculateTest() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(TimeType.MINUTE, now.minusMinutes(1), now),
                Arguments.of(TimeType.MINUTES, now.minusMinutes(1), now),
                Arguments.of(TimeType.HOUR, now.minusHours(1), now),
                Arguments.of(TimeType.HOURS, now.minusHours(1), now),
                Arguments.of(TimeType.DAY, now.minusDays(1), now),
                Arguments.of(TimeType.DAYS, now.minusDays(1), now),
                Arguments.of(TimeType.WEEK, now.minusWeeks(1), now),
                Arguments.of(TimeType.WEEKS, now.minusWeeks(1), now),
                Arguments.of(TimeType.MONTH, now.minusMonths(1), now),
                Arguments.of(TimeType.MONTHS, now.minusMonths(1), now)
        );
    }
}