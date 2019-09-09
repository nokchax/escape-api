package com.zum.escape.api.task;

import com.zum.escape.api.task.TaskService.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskService taskService;

    @Scheduled(cron = "0 0 1 * * MON")
    public void createTask() {
        taskService.createTasks();
    }

}
