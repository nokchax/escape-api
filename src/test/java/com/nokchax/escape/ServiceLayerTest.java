package com.nokchax.escape;

import com.nokchax.escape.leetcode.crawl.api.selenium.SeleniumPool;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class ServiceLayerTest extends CommonTest {
    @MockBean
    private SeleniumPool seleniumPool;
}
