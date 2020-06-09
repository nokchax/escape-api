package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

// load properties using @ConfigurationProperties annotation
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private TelegramBot telegram;
    private Master master;
    private Admin admin;
    private Selenium selenium;
    private HttpProperty httpProperty;
    private ThreadPool threadPool;
}
