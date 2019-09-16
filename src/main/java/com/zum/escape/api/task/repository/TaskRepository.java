package com.zum.escape.api.task.repository;

import com.zum.escape.api.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByStartDateTime(LocalDateTime startDateTime);
    boolean existsTaskByStartDateTime(LocalDateTime startDateTime);
}
