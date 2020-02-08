package com.nokchax.escape;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class EscapeApplication {
    // TODO: 2019-12-27 refactoring and construct dev environment (db, crawling)
    public static void main(String[] args) {
        // to init telegram bot
        ApiContextInitializer.init();

        new SpringApplicationBuilder()
                .sources(EscapeApplication.class)
                .properties("spring.config.additional-location=file:/data/etc/escape/token.yml")
                .run(args);
    }
}
