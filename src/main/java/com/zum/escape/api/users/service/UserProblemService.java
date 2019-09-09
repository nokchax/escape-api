package com.zum.escape.api.users.service;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.repository.UserProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProblemService {
    private final UserProblemRepository userProblemRepository;
    private final TaskService taskService;

    public List<UserProblem> findAllSolvedProblemsInThisWeek(User user) {

        return userProblemRepository.findByUserEqualsAndSolvedDateTimeBetween(
                user,
                taskService.getStartOfWeek(),
                taskService.getEndOfWeek()
        );
    }
}
