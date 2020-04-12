package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

// TODO: 2020-04-02 코드 리팩토링
@Slf4j
public class Selenium {
    private WebDriver browser;
    private WebDriverWait wait;
    private WebElement id;
    private WebElement password;

    private Selenium() {}

    public static Selenium openBrowser(UserAgentQueue userAgentQueue) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.merge(userAgentQueue.peek());

        Selenium newBrowser = new Selenium();
        newBrowser.browser = new ChromeDriver(options);
        newBrowser.wait = new WebDriverWait(newBrowser.browser, 10);

        return newBrowser;
    }

    public Selenium toLoginPage() {
        browser.get("https://leetcode.com/accounts/login/");

        return this;
    }

    public Selenium doLogin(User user) throws InterruptedException {
        id = browser.findElement(By.name("login"));
        password = browser.findElement(By.name("password"));
        Thread.sleep(2000);

        typeId(user.getId());
        typePassword(user.getPassword());

        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
        browser.navigate().to("https://leetcode.com/api/problems/all/");

        return this;
    }

    public LeetcodeApiResponse doCrawl() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        WebElement body = browser.findElement(By.tagName("body"));
        LeetcodeApiResponse problemResponse = objectMapper.readValue(body.getText(), LeetcodeApiResponse.class);

        log.debug("{}", problemResponse);

        closeBrowser();

        return problemResponse;
    }

    private void closeBrowser() {
        browser.close();
    }

    private void typeId(String userId) throws InterruptedException {
        id.click();
        humanizeTyping(id, userId);
        Thread.sleep(300);

        id.sendKeys(Keys.TAB);
    }

    private void typePassword(String userPassword) throws InterruptedException {
        password.clear();
        humanizeTyping(password, userPassword);
        Thread.sleep(300);

        password.sendKeys(Keys.RETURN);
    }

    private void humanizeTyping(WebElement element, String input) throws InterruptedException {
        for(char c : input.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            Thread.sleep(ThreadLocalRandom.current().nextLong(100) + 100);
        }
    }
}
