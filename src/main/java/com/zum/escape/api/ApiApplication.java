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
                .properties("spring.config.location=file:/data/etc/escape/application.yml")
                .run(args);
    }

}
