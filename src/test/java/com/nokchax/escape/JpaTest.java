package com.nokchax.escape;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DataJpaTest
@ActiveProfiles("dev")
public class JpaTest {
    protected void beforeQuery() {
        log.info("Before query ==============================================");
    }

    protected void afterQuery() {
        log.info("After query ===============================================");
    }

    protected void showResult() {
        log.info("Result ====================================================");
    }
}
