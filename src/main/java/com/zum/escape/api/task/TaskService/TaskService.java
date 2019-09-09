package com.zum.escape.api.task.TaskService;

import com.zum.escape.api.task.domain.DurationType;
import com.zum.escape.api.task.domain.Task;
import com.zum.escape.api.task.domain.TaskParticipant;
import com.zum.escape.api.task.repository.TaskRepository;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.service.UserProblemService;
import com.zum.escape.api.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UsersService usersService;
    private final UserProblemService userProblemService;

    @Transactional
    public void createTasks() {
        Task task = Task.builder()
                .startDateTime(getStartOfWeek())
                .endDateTime(getEndOfWeek())
                .goalScore(5)
                .durationType(DurationType.WEEK)
                .build();

        taskRepository.save(task);

        List<User> users = usersService.findAllUser();
        task.registerParticipants(users);
    }

    public List<User> getDoneList() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeek());

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

    public String toString(List<User> users) {
        if(users == null || users.isEmpty())
            return "Everyone completed weekly task";

        List<String> ids = new ArrayList<>(users.size());
        users.forEach(user -> ids.add(user.getUserId()));

        String usersId = String.join(", ", ids);

        return "[" + usersId + "]";
    }

    public List<User> getParticipants() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeek());

        List<User> participants = new ArrayList<>();

        currentTask.getParticipants().forEach(participant -> participants.add(participant.getUsers()));

        return participants;
    }

    @Transactional
    public List<User> update() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeek());
        List<User> participants = extractParticipants(currentTask);
        Map<User, TaskParticipant> userScores = extractTaskParticipant(currentTask);

        participants.forEach(participant -> {
            usersService.updateUser(participant);
            List<UserProblem> solvedProblems = userProblemService.findAllSolvedProblemsInThisWeek(participant);

            int score = calculateScore(solvedProblems);
            userScores.get(participant).updateScore(score);
        });

        return participants;
    }

    private Map<User, TaskParticipant> extractTaskParticipant(Task currentTask) {
        Map<User, TaskParticipant> userScores = new HashMap<>();
        currentTask.getParticipants().forEach(taskParticipant -> {
            User user = taskParticipant.getUsers();
            userScores.put(user, taskParticipant);
        });

        return userScores;
    }

    private List<User> extractParticipants(Task currentTask) {
        List<User> participants = new ArrayList<>();

        currentTask.getParticipants().forEach(participant -> participants.add(participant.getUsers()));

        return participants;
    }

    private int calculateScore(List<UserProblem> solvedProblems) {
        int score = solvedProblems.stream()
                .mapToInt(userProblem -> userProblem.getProblem().getDifficulty().getLevel()).sum();

        return score;
    }

    public LocalDateTime getStartOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();
    }

    public LocalDateTime getEndOfWeek() {
        return LocalDate.now()
                .with(DayOfWeek.SUNDAY)
                .atTime(23, 59, 59);
    }
}
