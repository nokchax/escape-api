package com.zum.escape.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        new SpringApplicationBuilder()
                .sources(ApiApplication.class)
                .properties("spring.config.additional-location=file:/data/etc/escape/token.yml")
                .run(args);
    }

}
