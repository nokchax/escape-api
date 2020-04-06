package com.nokchax.escape.schedule;

import com.nokchax.escape.leetcode.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProblemScheduler {
    private final UpdateService updateService;

    /** 문제 업데이트 */
    @Scheduled(cron = "0 30 1,13 * * SUN")
    public void saveOrUpdateProblems() {
        log.info("Update Problems Start");
        updateService.updateProblems();
        log.info("Update Problems End");
    }
}
