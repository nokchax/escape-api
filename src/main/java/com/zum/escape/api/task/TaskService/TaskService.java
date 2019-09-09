package com.zum.escape.api.task.TaskService;

import com.zum.escape.api.domain.entity.Difficulty;
import com.zum.escape.api.domain.entity.Problem;
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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    public static final int GOAL_SCORE = 5;
    private final TaskRepository taskRepository;
    private final UsersService usersService;
    private final UserProblemService userProblemService;
    private LocalDateTime lastUpdateTime;

    @PostConstruct
    public void updateLastUpdateTime() {
        this.lastUpdateTime = LocalDateTime.now();
    }

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

        return currentTask.getParticipants()
                .stream()
                .filter(TaskParticipant::hasReachedGaol)
                .map(TaskParticipant::getUsers)
                .collect(Collectors.toList());
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

        List<String> userIds = users.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());

        String usersId = String.join(", ", userIds);

        return "[" + usersId + "]";
    }

    public List<User> getParticipants() {
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeek());

        return currentTask.getParticipants()
                .stream()
                .map(TaskParticipant::getUsers)
                .collect(Collectors.toList());
    }

    @Transactional
    public String update() {
        if(!isUpdatable())
            return "Too many request, try after 10secs";
        Task currentTask = taskRepository.findByStartDateTime(getStartOfWeek());
        List<User> participants = extractParticipants(currentTask);
        Map<User, TaskParticipant> userScores = extractTaskParticipant(currentTask);

        participants.forEach(participant -> {
            usersService.updateUser(participant);
            List<UserProblem> solvedProblems = userProblemService.findAllSolvedProblemsInThisWeek(participant);

            int score = calculateScore(solvedProblems);
            userScores.get(participant).updateScore(score);
        });

        return toString(participants);
    }

    private boolean isUpdatable() {
        return LocalDateTime.now()
                .isAfter(lastUpdateTime.plusSeconds(10));
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

        return solvedProblems.stream()
                .map(UserProblem::getProblem)
                .map(Problem::getDifficulty)
                .mapToInt(Difficulty::getLevel)
                .sum();
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
