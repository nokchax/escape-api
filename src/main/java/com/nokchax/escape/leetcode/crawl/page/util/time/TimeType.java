package com.nokchax.escape.leetcode.crawl.page.util.time;

import com.nokchax.escape.exception.InvalidTimeType;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TimeType {
    MINUTE("minute", LocalDateTime::minusMinutes), MINUTES("minutes", LocalDateTime::minusMinutes),
    HOUR("hour", LocalDateTime::minusHours), HOURS("hours", LocalDateTime::minusHours),
    DAY("day", LocalDateTime::minusDays), DAYS("days", LocalDateTime::minusDays),
    WEEK("week", LocalDateTime::minusWeeks), WEEKS("weeks", LocalDateTime::minusWeeks),
    MONTH("month", LocalDateTime::minusMonths), MONTHS("months", LocalDateTime::minusMonths);

    private final String type;
    private final BiFunction<LocalDateTime, Integer, LocalDateTime> function;

    private static final Map<String, TimeType> TIME_TYPES = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(TimeType::getType, Function.identity()))
    );

    TimeType(String string, BiFunction<LocalDateTime, Integer, LocalDateTime> function) {
        this.function = function;
        this.type = string;
    }

    public LocalDateTime minus(LocalDateTime now, Integer time) {
        return function.apply(now, time);
    }

    public static TimeType of(String type) {
        typeValidation(type);
        type = type.toLowerCase();

        if(!TIME_TYPES.containsKey(type)) {
            throw new InvalidTimeType(type);
        }

        return TIME_TYPES.get(type);
    }

    private static void typeValidation(String type) {
        if(StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("Time type can't be a null or empty string");
        }
    }

    private String getType() {
        return type;
    }
}
