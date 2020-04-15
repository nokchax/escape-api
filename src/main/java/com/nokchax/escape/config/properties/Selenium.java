package com.nokchax.escape.config.properties;

import lombok.Data;

/*
    String driverPath = "/data/etc/webdriver/chromedriver";

    if(property.startsWith("Mac")) {
        driverPath = "/Users/nokchax" + driverPath;
    } else if(property.startsWith("Window")) {
        driverPath += ".exe";
    }
 */
@Data
public class Selenium {
    private String driverPath;
}
