package com.nokchax.escape.point.repository;

import com.nokchax.escape.point.dto.PointDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("dev")
class PointRepositoryTest {
    @Autowired
    private PointRepository pointRepository;

    @Test
    void getPointSumTest() {
        System.out.println("=========================================================Before query");
        List<PointDto> pointSummary = pointRepository.findAllUserPoint();
        System.out.println("=========================================================After query");

        pointSummary.forEach(System.out::println);
    }
}