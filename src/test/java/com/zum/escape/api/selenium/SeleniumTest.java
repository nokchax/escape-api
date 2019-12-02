package com.zum.escape.api.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
    @Test
    public void seleniumTest() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\KH\\Desktop\\chrome\\geckodriver.exe");
        WebDriver browser = new FirefoxDriver();
        browser.get("https://naver.com");

        System.out.println(browser.getTitle());
    }

}
