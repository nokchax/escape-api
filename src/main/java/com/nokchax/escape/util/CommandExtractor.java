package com.nokchax.escape.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CommandExtractor {
    private static final String DASH = "-";
    private static final String BLANK = " ";

    public static Map<String, String> extractOptions(String origin, String defaultOption) {
        log.debug("original command string : {}", origin);
        Map<String, String> options = new HashMap<>();
        String[] tokens = origin.trim()
                                .split(DASH);

        AtomicInteger index = new AtomicInteger();
        Arrays.stream(tokens)
                .forEach(token -> splitOption(token, options, defaultOption, index.getAndIncrement()));

        return options;
    }

    public static void splitOption(String token, Map<String, String> options, String defaultOption, int index) {
        log.debug("token : {}", token);
        token = token.trim();
        int splitIdx = token.indexOf(BLANK);

        if(splitIdx < 0) {
            return;
        }

        String key = index == 0 ? defaultOption : token.substring(0, splitIdx);
        String value = token.substring(splitIdx + 1);

        options.put(key, value);
    }
}
