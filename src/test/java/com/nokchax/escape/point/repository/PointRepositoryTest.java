package com.nokchax.escape.point.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PointRepositoryTest extends JpaTest {
    @Autowired
    private PointRepository pointRepository;

    @Test
    void findAllUsersPointInfoTest() {
        List<PointDto> usersPoint = pointRepository.findAllUserPoint();

        assertThat(usersPoint).isNotNull();
        assertThat(usersPoint.size()).isEqualTo(15);
        assertThat(usersPoint.get(0).getPoint()).isGreaterThan(usersPoint.get(usersPoint.size() - 1).getPoint());

        usersPoint.forEach(System.out::println);
    }

    @Test
    void findPenaltyUsersPointInfoTest() {
        List<PointDto> penaltyUsers = pointRepository.findAllPenaltyUsers();

        assertThat(penaltyUsers).isNotNull();
        assertThat(penaltyUsers.size()).isZero();
    }

    @Test
    void findPenaltyUsersPointInfoTest_Exist() {
        pointRepository.save(new Point(new User("nokchax1"), -100));

        List<PointDto> penaltyUsers = pointRepository.findAllPenaltyUsers();

        assertThat(penaltyUsers).isNotNull();
        assertThat(penaltyUsers.size()).isNotZero();

        penaltyUsers.forEach(System.out::println);
    }
}