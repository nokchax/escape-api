package com.zum.escape.api.scheduler;

import com.zum.escape.api.task.TaskService.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProblemHistoryScheduler {
    private final TaskService taskService;


    @Scheduled(cron = "0 1 0 * * SUN,TUE,WED,THU,FRI,SAT")
    public void correctUpdate() {
        log.info("Correct update occurred");
        taskService.correctUpdate();
    }


    @Scheduled(cron = "0 1 0 * * MON")
    public void correctUpdateLastWeek() {
        log.info("Correct update for last week's task occurred");
        taskService.correctUpdateLastWeek();
    }
}
