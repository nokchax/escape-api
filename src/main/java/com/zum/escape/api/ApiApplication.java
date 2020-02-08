package com.zum.escape.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

//@EnableScheduling
//@SpringBootApplication
//public class ApiApplication {
//
//    // TODO: 2019-12-27 refactoring and construct dev environment (db, crawling)
//    public static void main(String[] args) {
//        ApiContextInitializer.init();
//
//        new SpringApplicationBuilder()
//                .sources(ApiApplication.class)
//                .properties("spring.config.additional-location=file:/data/etc/escape/token.yml")
//                .run(args);
//    }
//}
