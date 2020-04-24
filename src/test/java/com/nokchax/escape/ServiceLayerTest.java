package com.nokchax.escape;

import com.nokchax.escape.leetcode.crawl.api.selenium.SeleniumPool;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@ActiveProfiles("dev")
@SpringBootTest(classes = ServiceLayerTest.TestApiApplication.class)
public class ServiceLayerTest extends CommonTest {
    @MockBean
    private SeleniumPool seleniumPool;

    @ComponentScan("com.nokchax.escape")
    @EnableAutoConfiguration(exclude = TelegramBotStarterConfiguration.class)
    public static class TestApiApplication {
        public static void main(String[] args) {
            new SpringApplicationBuilder()
                    .sources(com.nokchax.escape.ServiceLayerTest.TestApiApplication.class)
                    .run(args);
        }
    }
}
