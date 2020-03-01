package com.zum.escape.api.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

//@Slf4j
public class SeleniumTest {
//    @Test
//    public void seleniumTest() throws InterruptedException, IOException {
//        System.setProperty("webdriver.chrome.driver", "/Users/nokchax/data/etc/webdriver/chromedriver");
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
////        options.setHeadless(true);
//        // change user agent using randomization or round robin each request
//        options.merge(DesiredCapabilities.operaBlink());
//        WebDriver browser = new ChromeDriver(options);
//        WebDriverWait wait = new WebDriverWait(browser, 20);
//        browser.get("https://leetcode.com/accounts/login/");
//
//
//        WebElement id = browser.findElement(By.name("login"));
//        WebElement password = browser.findElement(By.name("password"));
//        Thread.sleep(2000);
//
//        id.click();
//        humanizeTyping(id, "");
//        Thread.sleep(300);
//        id.sendKeys(Keys.TAB);
//        password.clear();
//        humanizeTyping(password, "");
//        Thread.sleep(300);
//        password.sendKeys(Keys.RETURN);
////        button.click();
//
//        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
//        browser.navigate().to("https://leetcode.com/api/problems/all/");
//        WebElement body = browser.findElement(By.tagName("body"));
//
////        log.info(body.getText());
//        System.out.println(body.getText());
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ProblemResponse problemResponse = objectMapper.readValue(body.getText(), ProblemResponse.class);
//        System.out.println(problemResponse);
//
//        browser.close();
//    }
//
//    private void humanizeTyping(WebElement element, String input) throws InterruptedException {
//        for(char c : input.toCharArray()) {
//            element.sendKeys(String.valueOf(c));
//            Thread.sleep(ThreadLocalRandom.current().nextLong(100) + 100);
//        }
//    }

}
