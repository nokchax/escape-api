package com.nokchax.escape.leetcode.crawl.api.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class UserAgentQueue {
    private BlockingQueue<DesiredCapabilities> USER_AGENT_QUEUE = new LinkedBlockingQueue<>();

    @PostConstruct
    private void init() {
        List<DesiredCapabilities> capabilities = new ArrayList<>();

        capabilities.add(DesiredCapabilities.chrome());
        capabilities.add(DesiredCapabilities.firefox());
        capabilities.add(DesiredCapabilities.operaBlink());
        capabilities.add(DesiredCapabilities.safari());

        Collections.shuffle(capabilities);

        USER_AGENT_QUEUE.addAll(capabilities);
    }

    public DesiredCapabilities peek() {
        DesiredCapabilities frontUserAgent = USER_AGENT_QUEUE.poll();

        USER_AGENT_QUEUE.add(frontUserAgent);

        return frontUserAgent;
    }
}
