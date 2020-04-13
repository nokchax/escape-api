package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.nokchax.escape.leetcode.crawl.api.response.LeetcodeApiResponse;
import com.nokchax.escape.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "file:/data/etc/escape/pw.properties") //yml,yaml 파일은 로드를 못하네
class SeleniumTest {
    private UserAgentQueue userAgentQueue = new UserAgentQueue();

    @Value(value = "${test.pw:default}")
    private String pw;

    @Test
    void test() {
        System.out.println(pw);
    }

    void independentTest() throws InterruptedException, IOException {
        String property = System.getProperty("os.name");
        String driverPath = "/data/etc/webdriver/chromedriver";

        System.out.println(property);
        if(property.startsWith("Mac")) {
            driverPath = "/Users/nokchax" + driverPath;
        } else if(property.startsWith("Window")) {
            driverPath += ".exe";
        }

        System.setProperty("webdriver.chrome.driver", driverPath);
        Selenium selenium = Selenium.openBrowser(userAgentQueue);

        LeetcodeApiResponse leetcodeApiResponse = selenium.toLoginPage()
                .doLogin(new User("nokchax", pw, "name"))
                .doCrawl();

        log.info("leetcodeApiResponse = " + leetcodeApiResponse);
        Thread.sleep(2000);


        Selenium anotherSelenium = Selenium.openBrowser(userAgentQueue);

        LeetcodeApiResponse leetcodeApiResponse1 = anotherSelenium.toLoginPage()
                .doLogin(new User("blue44rain", pw, "name"))
                .doCrawl();

        log.info("leetcodeApiResponse1 = " + leetcodeApiResponse1);
        Thread.sleep(2000);


        LeetcodeApiResponse leetcodeApiResponse2 = selenium.doCrawl();

        log.info("leetcodeApiResponse2 = " + leetcodeApiResponse2);
        Thread.sleep(2000);

        LeetcodeApiResponse leetcodeApiResponse3 = anotherSelenium.doCrawl();

        log.info("leetcodeApiResponse3 = " + leetcodeApiResponse3);
        Thread.sleep(2000);


        LeetcodeApiResponse leetcodeApiResponse4 = selenium.doCrawl();
        log.info("leetcodeApiResponse4 = " + leetcodeApiResponse4);
        Thread.sleep(2000);

        LeetcodeApiResponse leetcodeApiResponse5 = anotherSelenium.doCrawl();
        log.info("leetcodeApiResponse5 = " + leetcodeApiResponse5);
        Thread.sleep(2000);

        selenium.closeBrowser();
        anotherSelenium.closeBrowser();
    }
}