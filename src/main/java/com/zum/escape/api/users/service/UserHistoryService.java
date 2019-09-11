package com.zum.escape.api.users.service;

import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.domain.Description;
import com.zum.escape.api.users.domain.Point;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserHistory;
import com.zum.escape.api.users.dto.PunishedUser;
import com.zum.escape.api.users.repository.UserHistoryRepository;
import com.zum.escape.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    public static final int MONTHLY_POINT = 5;
    private UserRepository userRepository;
    private UserHistoryRepository userHistoryRepository;
    private TaskService taskService;

    public void givePointToEveryUser(int point) {
        Point monthlyPoint = Point.makeMonthlyPoint(point , Description.PROVIDE_POINT);

        List<UserHistory> userHistories = userRepository.findAll()
                .stream()
                .map(user -> user.getPoints(monthlyPoint))
                .collect(Collectors.toList());

        userHistoryRepository.saveAll(userHistories);
    }

    public void givePointToOne(String leetcodeId, int point) {
        Point specialPoint = new Point(point, Description.PROVIDE_POINT);

        UserHistory userHistory = userRepository.findByLeetcodeName(leetcodeId)
                .getPoints(specialPoint);

        userHistoryRepository.save(userHistory);
    }

    public void imposeFines() {
        //find all user who didn't reached a goal
        List<PunishedUser> punishedUsers = taskService.getUsersNotSolvedProblemLastWeek();
        LocalDateTime imposedTime = LocalDateTime.now();

        List<UserHistory> userHistories = punishedUsers.stream()
                .map(punishedUser -> punishedUser.imposed(imposedTime))
                .collect(Collectors.toList());

        userHistoryRepository.saveAll(userHistories);
    }
}
