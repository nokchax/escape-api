package com.zum.escape.api.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
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

@Slf4j
public class Browser {
    private WebDriver browser;
    private WebDriverWait wait;
    private WebElement id;
    private WebElement password;

    private Browser() {}

    public static Browser openBrowser(UserAgentQueue userAgentQueue) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.merge(userAgentQueue.peek());

        Browser newBrowser = new Browser();
        newBrowser.browser = new ChromeDriver(options);
        newBrowser.wait = new WebDriverWait(newBrowser.browser, 20);

        return newBrowser;
    }

    public Browser toLoginPage() {
        browser.get("https://leetcode.com/accounts/login/");

        return this;
    }

    public Browser doLogin(User user) throws InterruptedException {
        id = browser.findElement(By.name("login"));
        password = browser.findElement(By.name("password"));
        Thread.sleep(2000);

        typeId(user.getId());
        typePassword(user.getPassword());

        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
        browser.navigate().to("https://leetcode.com/api/problems/all/");

        return this;
    }

    public ProblemResponse doCrawl() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        WebElement body = browser.findElement(By.tagName("body"));
        ProblemResponse problemResponse = objectMapper.readValue(body.getText(), ProblemResponse.class);

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
