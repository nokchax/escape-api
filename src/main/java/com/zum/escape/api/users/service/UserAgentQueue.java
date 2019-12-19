package com.zum.escape.api.users.service;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class UserAgentQueue {
    private BlockingQueue<DesiredCapabilities> USER_AGENT_QUEUE = new LinkedBlockingQueue<>();

    @PostConstruct
    private void init() {
        USER_AGENT_QUEUE.add(DesiredCapabilities.chrome());
        USER_AGENT_QUEUE.add(DesiredCapabilities.firefox());
        USER_AGENT_QUEUE.add(DesiredCapabilities.operaBlink());
        USER_AGENT_QUEUE.add(DesiredCapabilities.safari());
    }

    public DesiredCapabilities peek() {
        DesiredCapabilities frontUserAgent = USER_AGENT_QUEUE.poll();

        USER_AGENT_QUEUE.add(frontUserAgent);

        return frontUserAgent;
    }
}
