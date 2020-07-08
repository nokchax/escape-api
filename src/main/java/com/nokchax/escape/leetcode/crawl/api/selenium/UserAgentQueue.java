package com.nokchax.escape.leetcode.crawl.api.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class UserAgentQueue {
    private final BlockingQueue<DesiredCapabilities> USER_AGENT_QUEUE = new LinkedBlockingQueue<>(
            Arrays.asList(
                    DesiredCapabilities.chrome(),
                    DesiredCapabilities.firefox(),
                    DesiredCapabilities.operaBlink(),
                    DesiredCapabilities.safari()
            )
    );

    public DesiredCapabilities peek() {
        if (USER_AGENT_QUEUE.isEmpty()) {
            throw new IllegalArgumentException("No user agent");
        }

        DesiredCapabilities frontUserAgent = USER_AGENT_QUEUE.poll();
        USER_AGENT_QUEUE.add(frontUserAgent);
        return frontUserAgent;
    }
}
