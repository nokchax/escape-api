package com.zum.escape.api.users.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProblemCrawlService {
    private final UserAgentQueue userAgentQueue;

    // TODO: 2019/12/19 hard coding :(
    @PostConstruct
    private void init() {
        String property = System.getProperty("os.name");
        String driverPath = "/data/etc/webdriver/chromedriver";
        if(property.startsWith("Mac")) {
            driverPath = "/Users/nokchax" + driverPath;
        }

        System.setProperty("webdriver.chrome.driver", driverPath);
    }

    public ProblemResponse getUserProblems(User user) {
        try {
            // this pattern is not good.. when process order is important
            return Browser.openBrowser(userAgentQueue)
                    .toLoginPage()
                    .doLogin(user)
                    .doCrawl();
        } catch (InterruptedException e) {
            log.error("Interrupted : {}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("Timeout exception(maybe effected by recaptcha) : {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
        }

        return null;
    }
}
