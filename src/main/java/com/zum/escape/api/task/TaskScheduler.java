package com.zum.escape.api.task;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskService taskService;
    private final UserHistoryService userHistoryService;

    @Scheduled(cron = "0 2 0 * * MON")
    public void createTask() {
        taskService.createTasks();
    }

    @Scheduled(cron = "30 2 0 * * MON")
    public void updatePoints() {
        userHistoryService.imposeFines();
    }

    @Scheduled(cron = "0 0 0,9,12,15,18,21 * * *")
    public void update() {
        taskService.update();
    }
}
