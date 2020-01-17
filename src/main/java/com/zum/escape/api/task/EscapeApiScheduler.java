package com.zum.escape.api.task;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EscapeApiScheduler {
    private final TaskService taskService;
    private final UserHistoryService userHistoryService;

    @Scheduled(cron = "0 1 0 * * MON")
    public void createTask() {
        // make weekly task
        log.info("create task start");
        taskService.createTasks();
        log.info("create task end");
    }

    @Scheduled(cron = "30 1 0 * * MON")
    public void updatePoints() {
        // subtract user's points when weekly tasks ends
        log.info("update points start");
        taskService.correctUpdateLastWeek();
        userHistoryService.imposeFines();
        log.info("update points end");
    }

    // monday 00:00 scheduling occurs null point exception (cuz curtask user is empty)
    @Scheduled(cron = "0 0 0,9,12,15,18,21 * * SUN,TUE,WED,THU,FRI,SAT")
    public void update() {
        // update user's status
        log.info("update start");
        taskService.update();
        log.info("update end");
    }

    @Scheduled(cron = "0 0 9,12,15,18,21 * * MON")
    public void updateOfMonday() {
        // update user's status
        log.info("update of monday start");
        this.update();
        log.info("update of monday end");
    }
}
