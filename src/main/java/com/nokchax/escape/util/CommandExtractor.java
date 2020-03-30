package com.nokchax.escape.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandExtractor {
    private static final String DASH = "-";
    private static final String BLANK = " ";

    public static Map<String, String> extractOptions(String origin, String defaultOption) {
        log.debug("original command string : {}", origin);
        Map<String, String> options = new HashMap<>();
        String[] tokens = origin.trim()
                                .split(DASH);

        Arrays.stream(tokens)
                .forEach(token -> splitOption(token, options, defaultOption));

        return options;
    }

    public static void splitOption(String token, Map<String, String> options, String defaultOption) {
        log.debug("token : {}", token);
        int splitIdx = token.trim().indexOf(BLANK);

        if(splitIdx < 0) {
            return;
        }

        // TODO: 2020-03-30 명령어가 /로 시작하지 않는다면... 문제가 될 코드
        String key = token.startsWith("/") ? defaultOption : token.trim().substring(0, splitIdx);
        String value = token.trim().substring(splitIdx + 1);

        options.put(key, value);
    }
}
