package com.zum.escape.api.users.service;

import com.zum.escape.api.users.domain.Description;
import com.zum.escape.api.users.domain.Point;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserHistory;
import com.zum.escape.api.users.repository.UserHistoryRepository;
import com.zum.escape.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    public static final int MONTHLY_POINT = 5;
    private UserRepository userRepository;
    private UserHistoryRepository userHistoryRepository;

    public void givePointToEveryUser(int point) {
        Point monthlyPoint = Point.makeMonthlyPoint(point , Description.PROVIDE_POINT);

        List<UserHistory> userHistories = userRepository.findAll()
                .stream()
                .map(user -> user.getPoints(monthlyPoint))
                .collect(Collectors.toList());

        userHistoryRepository.saveAll(userHistories);
    }

    public void givePointToOne(String leetcodeId, int point) {
        Point specialPoint = new Point(1, Description.PROVIDE_POINT);

        UserHistory userHistory = userRepository.findByLeetcodeName(leetcodeId)
                .getPoints(specialPoint);

        userHistoryRepository.save(userHistory);
    }
}
