package com.zum.escape.api.task.TaskService;

import com.zum.escape.api.problem.domain.entity.Difficulty;
import com.zum.escape.api.problem.domain.entity.Problem;
import com.zum.escape.api.task.domain.DurationType;
import com.zum.escape.api.task.domain.Task;
import com.zum.escape.api.task.domain.TaskParticipant;
import com.zum.escape.api.task.repository.TaskRepository;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.dto.PunishedUser;
import com.zum.escape.api.users.dto.UserDto;
import com.zum.escape.api.users.service.UserProblemService;
import com.zum.escape.api.users.service.UsersService;
import com.zum.escape.api.util.DateTimeMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
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

        updateUser(participants);
        participants.forEach(participant -> {
            //usersService.updateUser(participant);
            List<UserProblem> userProblems = userProblemService.findAllSolvedProblemsInThisWeek(participant);
            userScores.get(participant)
                    .updateScore(
                            calculateScore(userProblems),
                            calculateHardCount(userProblems),
                            calculateMediumCount(userProblems),
                            calculateEasyCount(userProblems)
                    );
        });



        updateLastUpdateTime();

        return "update complete";
    }

    public void updateUser(List<User> participants) {
        log.info("UPDATE USER STARTED");
        List<CompletableFuture<User>> futures = participants.stream()
                .map(usersService::updateUser)
                .collect(Collectors.toList());


        log.info("WAITING CRAWLING USERS");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .join();

        log.info("UPDATE USER END");
    }

    public void createTasks() {
        if(taskRepository.existsTaskByStartDateTime(DateTimeMaker.getStartOfWeek()))
            return;

        Task task = Task.builder()
                .startDateTime(DateTimeMaker.getStartOfWeek())
                .endDateTime(DateTimeMaker.getEndOfWeek())
                .goalScore(GOAL_SCORE)
                .durationType(DurationType.WEEK)
                .build();

        taskRepository.save(task);

        task.registerParticipants(usersService.findAllUser());
    }

    public void updateTask() {
        Task task = taskRepository.findByStartDateTime(DateTimeMaker.getStartOfWeek());

        task.updateParticipants(usersService.findAllUser());
    }

    public List<UserDto> getDoneList() {
        return getCurrentTask().getParticipants()
                .stream()
                .filter(TaskParticipant::hasReachedGoal)
                .map(TaskParticipant::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getTodoList() {
        return getCurrentTask().getParticipants()
                .stream()
                .filter(TaskParticipant::hasNotReachedGoal)
                .map(TaskParticipant::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getAllUsers() {
        update();

        return getCurrentTask().getParticipants()
                        .stream()
                        .map(TaskParticipant::toUserDto)
                        .collect(Collectors.toList());
    }

    public List<PunishedUser> getUsersNotSolvedProblemLastWeek() {
        return getLastTask().getParticipants()
                .stream()
                .filter(TaskParticipant::hasNotReachedGoal)
                .map(TaskParticipant::toPunishedUser)
                .collect(Collectors.toList());
    }

    private Task getCurrentTask() {
        return taskRepository.findByStartDateTime(DateTimeMaker.getStartOfWeek());
    }

    private Task getLastTask() {
        return taskRepository.findByStartDateTime(DateTimeMaker.getStartOfLastWeek());
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

    public void correctUpdate() {
        getCurrentTask().getParticipants()
                .stream()
                .map(TaskParticipant::getUsers)
                .forEach(user -> usersService.updateAllSolvedHistory(user, DateTimeMaker.getYesterday()));
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

    private int calculateHardCount(List<UserProblem> solvedProblems) {
        return (int) solvedProblems.stream()
                .map(UserProblem::getProblem)
                .filter(Problem::isHard)
                .count();
    }

    private int calculateMediumCount(List<UserProblem> solvedProblems) {
        return (int) solvedProblems.stream()
                .map(UserProblem::getProblem)
                .filter(Problem::isMedium)
                .count();
    }

    private int calculateEasyCount(List<UserProblem> solvedProblems) {
        return (int) solvedProblems.stream()
                .map(UserProblem::getProblem)
                .filter(Problem::isEasy)
                .count();
    }

    public String updateSpecificUser(String userId) {
        usersService.updateUser(userId);
        Optional<TaskParticipant> participant = getCurrentTask().getParticipants()
                .stream()
                .filter(taskParticipant -> taskParticipant.getUsers().getId().equalsIgnoreCase(userId))
                .findFirst();

        List<UserProblem> userProblems = userProblemService.findAllSolvedProblemsInThisWeek(participant.get().getUsers());
        participant.get().updateScore(
                calculateScore(userProblems),
                calculateHardCount(userProblems),
                calculateMediumCount(userProblems),
                calculateEasyCount(userProblems)
        );
        return participant.get().toUserDto().toMessage();
    }
}
