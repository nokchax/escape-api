package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.dto.UserPointDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("dev")
class UserPointHistoryRepositoryTest {
    @Autowired
    private UserPointHistoryRepository userPointHistoryRepository;

    @Test
    void getPointSumTest() {
        System.out.println("=========================================================Before query");
        List<UserPointDto> pointSummary = userPointHistoryRepository.findAllUserPointHistoryWithOrdering();
        System.out.println("=========================================================After query");

        pointSummary.forEach(System.out::println);
    }
}