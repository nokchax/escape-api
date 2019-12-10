package com.zum.escape.api.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Service
public class UserProblemCrawlService {
    private static final String DRIVER_PATH = "/src/main/resources/webdriver/geckodriver(linux)";

    @PostConstruct
    private void init() {
        ClassPathResource resource = new ClassPathResource(DRIVER_PATH);
        System.setProperty("webdriver.gecko.driver", resource.getPath());
    }

    public ProblemResponse getUserProblems(User user) throws IOException, InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
//        options.setHeadless(true);
        // change user agent using randomization or round robin each request
        options.merge(DesiredCapabilities.operaBlink());
        WebDriver browser = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 20);
        browser.get("https://leetcode.com/accounts/login/");


        WebElement id = browser.findElement(By.name("login"));
        WebElement password = browser.findElement(By.name("password"));
        Thread.sleep(2000);

        id.click();
        humanizeTyping(id, "blue44rain");
        Thread.sleep(300);
        id.sendKeys(Keys.TAB);
        password.clear();
        humanizeTyping(password, "zum123");
        Thread.sleep(300);
        password.sendKeys(Keys.RETURN);
//        button.click();

        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"nav-user-app\"]")));
        browser.navigate().to("https://leetcode.com/api/problems/all/");
        WebElement body = browser.findElement(By.tagName("body"));

//        log.info(body.getText());
        System.out.println(body.getText());
        ObjectMapper objectMapper = new ObjectMapper();

        ProblemResponse problemResponse = objectMapper.readValue(body.getText(), ProblemResponse.class);
        System.out.println(problemResponse);

        browser.close();

        return problemResponse;
    }

    private void humanizeTyping(WebElement element, String input) throws InterruptedException {
        for(char c : input.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            Thread.sleep(ThreadLocalRandom.current().nextLong(100) + 100);
        }
    }
}
