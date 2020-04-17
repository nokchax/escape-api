package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokchax.escape.exception.CrawlException;
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

import java.util.concurrent.ThreadLocalRandom;

import static com.nokchax.escape.leetcode.crawl.Leetcode.LEETCODE_API_URL;
import static com.nokchax.escape.leetcode.crawl.Leetcode.LEETCODE_LOGIN_PAGE;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Slf4j
public class SeleniumBrowser {
    private final ObjectMapper objectMapper;
    private final String userId;
    private WebDriver browser;
    private WebDriverWait wait;

    public SeleniumBrowser(User user, UserAgentQueue userAgentQueue, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.userId = user.getId();
        initDriver(userAgentQueue);
        login(user);
    }

    private void login(User user) {
        browser.get(LEETCODE_LOGIN_PAGE);
        WebElement idElement = browser.findElement(By.name("login"));
        WebElement passwordElement = browser.findElement(By.name("password"));

        sleep(2000);

        typeId(idElement, user.getId());
        typePassword(passwordElement, user.getPassword());

        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
    }

    public LeetcodeApiResponse crawlApi() {
        log.info("Selenium api crawl start : " + userId);
        WebElement apiResponse = getApiResponse();
        log.info("Selenium api crawl end");

        return convert(apiResponse);
    }

    private LeetcodeApiResponse convert(WebElement body) {
        LeetcodeApiResponse apiResponse = convertToObject(body);

        if(!apiResponse.isValidCrawl(userId)) {
            throw new CrawlException("Maybe login session out for [" + userId + "]");
        }

        return apiResponse;
    }

    private LeetcodeApiResponse convertToObject(WebElement body) {
        try {
            return objectMapper.readValue(body.getText(), LeetcodeApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new CrawlException("Maybe api response template changed : Detail [" + e.getMessage() + "]");
        }
    }

    private WebElement getApiResponse() {
        browser.navigate()
                .to(LEETCODE_API_URL);

        return browser.findElement(By.tagName("body"));
    }

    public void close() {
        browser.close();
    }

    private void initDriver(UserAgentQueue userAgentQueue) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.merge(userAgentQueue.peek());

        this.browser = new ChromeDriver(options);
        this.wait = new WebDriverWait(this.browser, 10);
    }

    private void typeId(WebElement idElement, String userId) {
        idElement.click();
        humanizeTyping(idElement, userId);
        sleep(300);

        idElement.sendKeys(Keys.TAB);
    }

    private void typePassword(WebElement passwordElement, String userPassword) {
        passwordElement.clear();
        humanizeTyping(passwordElement, userPassword);
        sleep(300);

        passwordElement.sendKeys(Keys.RETURN);
    }

    private void humanizeTyping(WebElement element, String input) {
        for(char c : input.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep((int) (ThreadLocalRandom.current().nextLong(100) + 100));
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new CrawlException("Fail to sleep");
        }
    }
}
