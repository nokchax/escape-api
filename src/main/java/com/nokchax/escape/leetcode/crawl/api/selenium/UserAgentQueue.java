package com.nokchax.escape.leetcode.crawl.api.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class UserAgentQueue {
    private BlockingQueue<DesiredCapabilities> USER_AGENT_QUEUE = new LinkedBlockingQueue<>(
            Arrays.asList(
                    DesiredCapabilities.chrome(),
                    DesiredCapabilities.firefox(),
                    DesiredCapabilities.operaBlink(),
                    DesiredCapabilities.safari()
            )
    );

    public DesiredCapabilities peek() {
        DesiredCapabilities frontUserAgent = USER_AGENT_QUEUE.poll();

        USER_AGENT_QUEUE.add(frontUserAgent);

        return frontUserAgent;
    }
}
