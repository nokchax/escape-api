package com.nokchax.escape;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
public class ServiceLayerTest {
    protected void beforeQuery() {
        log.info("Before query=======================================");
    }

    protected void afterQuery() {
        log.info("After query========================================");
    }
}
