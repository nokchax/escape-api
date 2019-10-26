package com.zum.escape.api.scheduler;

import com.zum.escape.api.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProblemScheduler {
    private final ProblemService problemService;

    @PostConstruct
    @Scheduled(cron = "0 30 1,13 * * SUN")
    public void saveOrUpdateProblems() {
        log.info("Save Or Update Problems Start");
        problemService.saveOrUpdateProblems();
        log.info("Save Or Update Problems End");
    }

}
