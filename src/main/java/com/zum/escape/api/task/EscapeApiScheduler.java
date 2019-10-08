package com.zum.escape.api.task;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EscapeApiScheduler {
    private final TaskService taskService;
    private final UserHistoryService userHistoryService;

    @Scheduled(cron = "0 1 0 * * MON")
    public void createTask() {
        // make weekly task
        taskService.createTasks();
    }

    @Scheduled(cron = "30 1 0 * * MON")
    public void updatePoints() {
        // give points to all users when month start
        userHistoryService.imposeFines();
    }

    @Scheduled(cron = "0 0 0,9,12,15,18,21 * * *")
    public void update() {
        // update user's status
        taskService.update();
    }
}
