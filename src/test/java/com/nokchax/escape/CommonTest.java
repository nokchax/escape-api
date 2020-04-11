package com.nokchax.escape;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonTest {
    protected void beforeQuery() {
        log.info("Before query===============================================");
    }

    protected void afterQuery() {
        log.info("After query================================================");
    }

    protected void beforeClear() {
        log.info("Before clear===============================================");
    }

    protected void afterClear() {
        log.info("After clear================================================");
    }

    protected void showResult() {
        log.info("Result ====================================================");
    }
}
