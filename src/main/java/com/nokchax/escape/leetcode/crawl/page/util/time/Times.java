package com.nokchax.escape.leetcode.crawl.page.util.time;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Times {
    private List<Time> times;

    private Times() {}

    // https://m.blog.naver.com/PostView.nhn?blogId=bb_&logNo=221449936369&proxyReferer=https:%2F%2Fwww.google.com%2F
    public static Times of(String timeString) {
        Times times = new Times();

        times.times = Arrays.stream(timeString.split(","))
                .map(time -> time.replace(((char) 160), ' ')) //160이 뭔 문자인지 모르겠지만 공백을 의미
                .map(String::trim)
                .map(Time::new)
                .collect(Collectors.toList());

        return times;
    }

    public LocalDateTime calculate() {
        return times.stream()
                .reduce(Time.ofNow(), (beforeTime, afterTime) -> afterTime.calculate(beforeTime))
                .getByTime();
    }
}
