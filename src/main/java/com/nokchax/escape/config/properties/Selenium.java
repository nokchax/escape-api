package com.nokchax.escape.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
    String driverPath = "/data/etc/webdriver/chromedriver";

    if (property.startsWith("Mac")) {
        driverPath = "/Users/nokchax" + driverPath;
    } else if (property.startsWith("Window")) {
        driverPath += ".exe";
    }
 */
@Data
@ConfigurationProperties(prefix = "selenium")
public class Selenium {
    private String driverType;
    private String driverPath;
}
