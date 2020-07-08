package com.nokchax.escape;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan("com.nokchax.escape.config")
public class EscapeApplication {
    private static final String EXTERNAL_CONFIG_FILE = "/data/etc/escape/escape-api.yml";

    public static void main(String[] args) {
        // to init telegram bot
        ApiContextInitializer.init();

        new SpringApplicationBuilder()
                .sources(EscapeApplication.class)
                .properties("spring.config.additional-location=file:" + getConfigFilePath())
                .run(args);
    }

    private static String getConfigFilePath() {
        String os = System.getProperty("os.name");

        if (StringUtils.isEmpty(os) || !os.startsWith("Mac")) {
            return EXTERNAL_CONFIG_FILE;
        }

        return "/Users/nokchax" + EXTERNAL_CONFIG_FILE;
    }
}
