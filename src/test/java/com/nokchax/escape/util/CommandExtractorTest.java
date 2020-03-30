package com.nokchax.escape.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommandExtractorTest {

    @Test
    void testExtractor() {
        String command = "/user -u id -p 123 -n 이름";

        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.get("u")).isEqualTo("id");
        assertThat(options.get("p")).isEqualTo("123");
        assertThat(options.get("n")).isEqualTo("이름");
    }

    @Test
    void testExtractorWithDefaultArgument() {
        String command = "/user nokchax -p 123 -n 이름";

        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.get("u")).isEqualTo("nokchax");
        assertThat(options.get("p")).isEqualTo("123");
        assertThat(options.get("n")).isEqualTo("이름");
    }

    @Test
    void testExtractorWithNoArgument() {
        String command = "/user";

        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.size()).isZero();
    }
}