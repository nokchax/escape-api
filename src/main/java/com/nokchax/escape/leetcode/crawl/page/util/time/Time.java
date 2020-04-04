package com.nokchax.escape.leetcode.crawl.page.util.time;

import com.nokchax.escape.exception.InvalidTimeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Time {
    private LocalDateTime byTime;
    private Integer timeValue;
    private TimeType type;

    private Time() {}

    public Time(String timeString) {
        String[] inputs = timeString.split("\\s+");

        if(inputs.length < 2) {
            throw new InvalidTimeType(timeString);
        }

        this.timeValue = Integer.parseInt(inputs[0].trim());
        this.type = TimeType.of(inputs[1].trim());
    }

    public static Time ofNow() {
        Time now = new Time();

        now.byTime = LocalDateTime.now();

        return now;
    }

    public LocalDateTime calculate(LocalDateTime time) {
        return type.minus(time, timeValue);
    }

    public Time calculate(Time time) {
        byTime = type.minus(time.byTime, timeValue);

        return this;
    }
}
