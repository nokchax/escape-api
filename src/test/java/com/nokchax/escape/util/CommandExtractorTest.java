package com.nokchax.escape.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommandExtractorTest {

    @ParameterizedTest
    @ValueSource(strings = {"/user -u id -p 123 -n 이름", "user -u id -p 123 -n 이름", "/u -u id -p 123 -n 이름"})
    void testExtractor(String command) {
        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.get("u")).isEqualTo("id");
        assertThat(options.get("p")).isEqualTo("123");
        assertThat(options.get("n")).isEqualTo("이름");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user nokchax -p 123 -n 이름", "user nokchax -p 123 -n 이름"})
    void testExtractorWithDefaultArgument(String command) {
        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.get("u")).isEqualTo("nokchax");
        assertThat(options.get("p")).isEqualTo("123");
        assertThat(options.get("n")).isEqualTo("이름");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user", "user"})
    void testExtractorWithNoArgument(String command) {
        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.size()).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user something special", "user something special", "/u something special"})
    void testExtractorWithEmptyStringIncluded() {
        String command = "/user something special";

        Map<String, String> options = CommandExtractor.extractOptions(command, "u");

        assertThat(options.get("u")).isEqualTo("something special");
    }
}