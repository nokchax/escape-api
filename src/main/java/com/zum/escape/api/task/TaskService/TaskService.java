package com.zum.escape.api.task.TaskService;

import com.zum.escape.api.task.domain.Task;
import com.zum.escape.api.task.repository.TaskRepository;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UsersService usersService;

    public List<User> getDoneList() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeed());

        List<User> users = new ArrayList<>();
        currentTask.getDoneUser()
                .forEach(doneUser -> users.add(doneUser.getUsers()));

        return users;
    }

    public List<User> getTodoList() {
        List<User> allUser = getParticipants();
        List<User> doneUsers = getDoneList();

        doneUsers.forEach(allUser::remove);
        return allUser;
    }

    public List<User> getParticipants() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeed());

        List<User> participants = new ArrayList<>();

        currentTask.getParticipants().forEach(participant -> participants.add(participant.getUsers()));

        return participants;
    }

    public List<User> update() {
        List<User> participants = getParticipants();
        participants.forEach(participant -> {
            if(usersService.checkUserSolvedProblem(participant.getUserId())) {
            }
        });

        return participants;
    }

    private LocalDateTime getStartOfWeed() {
        return LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();
    }
}
