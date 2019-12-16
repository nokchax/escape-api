package com.zum.escape.api.users.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProblemCrawlService {
    // TODO: 2019/12/16 change web driver from firefox to chrome
    private static final String DRIVER_PATH = "/webdriver/geckodriver";
    private final UserAgentQueue userAgentQueue;

    @PostConstruct
    private void init() {
        ClassPathResource resource = new ClassPathResource(DRIVER_PATH);
        System.setProperty("webdriver.gecko.driver", resource.getPath());
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
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
        }

        return null;
    }
}
