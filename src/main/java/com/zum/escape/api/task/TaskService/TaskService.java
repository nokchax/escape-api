package com.zum.escape.api.task.TaskService;

import com.zum.escape.api.domain.entity.Difficulty;
import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.task.domain.DurationType;
import com.zum.escape.api.task.domain.Task;
import com.zum.escape.api.task.domain.TaskParticipant;
import com.zum.escape.api.task.repository.TaskRepository;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.dto.UserDto;
import com.zum.escape.api.users.service.UserProblemService;
import com.zum.escape.api.users.service.UsersService;
import com.zum.escape.api.util.DateTimeMaker;
import com.zum.escape.api.util.MessageMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public String update() {
        if(!isUpdatable())
            return "Too many request, try after 10secs";

        Task currentTask = getCurrentTask();
        List<User> participants = extractParticipants(currentTask);
        Map<User, TaskParticipant> userScores = extractTaskParticipant(currentTask);

        participants.forEach(participant -> {
            usersService.updateUser(participant);
            List<UserProblem> solvedProblems = userProblemService.findAllSolvedProblemsInThisWeek(participant);

            int score = calculateScore(solvedProblems);
            userScores.get(participant).updateScore(score);
        });


        updateLastUpdateTime();

        return MessageMaker.userDtoToMessage(
                currentTask.getParticipants()
                        .stream()
                        .map(TaskParticipant::toUserDto)
                        .collect(Collectors.toList())
                , "There are no participants"
        );
    }

    public void createTasks() {
        Task task = Task.builder()
                .startDateTime(DateTimeMaker.getStartOfWeek())
                .endDateTime(DateTimeMaker.getEndOfWeek())
                .goalScore(GOAL_SCORE)
                .durationType(DurationType.WEEK)
                .build();

        taskRepository.save(task);

        List<User> users = usersService.findAllUser();
        task.registerParticipants(users);
    }

    public List<UserDto> getDoneList() {
        update();

        return getCurrentTask().getParticipants()
                .stream()
                .filter(TaskParticipant::hasReachedGoal)
                .map(TaskParticipant::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getTodoList() {
        update();

        return getCurrentTask().getParticipants()
                .stream()
                .filter(TaskParticipant::hasNotReachedGoal)
                .map(TaskParticipant::toUserDto)
                .collect(Collectors.toList());
    }

    private Task getCurrentTask() {
        return taskRepository.findByStartDateTime(DateTimeMaker.getStartOfWeek());
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
        return currentTask.getParticipants()
                .stream()
                .map(TaskParticipant::getUsers)
                .collect(Collectors.toList());
    }

    private int calculateScore(List<UserProblem> solvedProblems) {
        return solvedProblems.stream()
                .map(UserProblem::getProblem)
                .map(Problem::getDifficulty)
                .mapToInt(Difficulty::getLevel)
                .sum();
    }
}