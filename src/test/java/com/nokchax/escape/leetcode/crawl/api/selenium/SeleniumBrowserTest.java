package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
class SeleniumBrowserTest {
    private static final String PROPERTY_PATH = "/data/etc/escape/pw.properties";
    private static final UserAgentQueue userAgentQueue = new UserAgentQueue();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static SeleniumBrowser seleniumBrowser;

    @BeforeAll
    static void init() {
        System.setProperty("webdriver.chrome.driver", "/data/etc/webdriver/chromedriver.exe");
        User user = new User("nokchax", getPassword(), "test");
        seleniumBrowser = SeleniumBrowser.of(user, userAgentQueue, objectMapper);
    }


    @Test
    @DisplayName("셀레니움 브라우저가 제대로 초기화 되었는지 테스트")
    void initTest() {
        assertThat(seleniumBrowser).isNotNull();
    }

    @Test
    @DisplayName("api로 부터 데이터를 잘 가져오는지 확인")
    void crawlTest() {
        LeetcodeApiResponse apiResponse = seleniumBrowser.crawlApi();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getUserId()).isEqualTo("nokchax");
        assertThat(apiResponse.isValidCrawl("nokchax")).isTrue();
        assertThat(apiResponse.getTotalProblemCount()).isNotZero();
        assertThat(apiResponse.getSolvedProblemCount()).isNotZero();
    }

    private static String getPassword() {
        try {
            return new Scanner(new File(PROPERTY_PATH)).nextLine()
                    .split("=")[1]
                    .trim();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fail to load properties file, check file exist");
        }
    }
}