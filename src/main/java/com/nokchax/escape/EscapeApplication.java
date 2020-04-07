package com.nokchax.escape;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@SpringBootApplication
public class EscapeApplication {
    public static void main(String[] args) {
        // to init telegram bot
//        ApiContextInitializer.init();

        new SpringApplicationBuilder()
                .sources(EscapeApplication.class)
                .properties("spring.config.additional-location=file:/data/etc/escape/token.yml")
                .run(args);
    }
}
