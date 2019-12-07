package com.zum.escape.api.selenium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ClassPathResource;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Slf4j
public class SeleniumTest {
    @Test
    public void seleniumTest() {
        ClassPathResource resource = new ClassPathResource("/src/main/resources/webdriver/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", resource.getPath());

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        WebDriver browser = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 20);
        browser.get("https://leetcode.com/accounts/login/");


        WebElement button = wait.until(presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div/button")));

        WebElement id = browser.findElement(By.name("login"));
        WebElement password = browser.findElement(By.name("password"));

        id.clear();
        id.sendKeys("nokchax");
        password.clear();
        password.sendKeys("");
        button.click();

        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
        browser.navigate().to("https://leetcode.com/api/problems/all/");
        WebElement body = browser.findElement(By.tagName("body"));

        log.info(body.getText());

        browser.close();
    }

}
