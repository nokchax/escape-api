package com.zum.escape.api.thirdPartyAdapter.leetcode.service;

import com.zum.escape.api.users.domain.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class LeetCodeCrawlService {
    private static final String LEETCODE_LOGIN_URL = "https://leetcode.com/accounts/login/";
    private static final String LEETCODE_API_URL = "https://leetcode.com/api/problems/all/";
    private static final String XPATH_OF_LOGIN_BUTTON = "/html/body/div[1]/div/div[2]/div/div[2]/div/div/div/button";
    private static final String XPATH_OF_NAV_BUTTON = "//*[@id=\"nav-user-app\"]";
    private FirefoxOptions options;

    @PostConstruct
    public void init() {
        ClassPathResource resource = new ClassPathResource("/src/main/resources/webdriver/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", resource.getPath());

        options = new FirefoxOptions();
        options.setHeadless(true);
    }

    public void crawl(User user) {
        WebDriver browser = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 20);
        browser.get(LEETCODE_LOGIN_URL);


        WebElement button = wait.until(presenceOfElementLocated(By.xpath(XPATH_OF_LOGIN_BUTTON)));

        WebElement id = browser.findElement(By.name("login"));
        WebElement password = browser.findElement(By.name("password"));

        id.clear();
        id.sendKeys(user.getId());
        password.clear();
        password.sendKeys(user.getPassword());
        button.click();

        wait.until(presenceOfElementLocated(By.xpath(XPATH_OF_NAV_BUTTON)));
        browser.navigate().to(LEETCODE_API_URL);
        WebElement body = browser.findElement(By.tagName("body"));

        System.out.println(body.getText());

        // TODO: 2019/12/03 do not close browser and reuse it
        browser.close();
    }
}
