package com.zum.escape.api.task.repository;

import com.zum.escape.api.task.domain.Task;
import com.zum.escape.api.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByStartDateTime(LocalDateTime startDateTime);
}
