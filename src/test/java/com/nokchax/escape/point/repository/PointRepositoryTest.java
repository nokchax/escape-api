package com.nokchax.escape.point.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PointRepositoryTest extends JpaTest {
    @Autowired
    private PointRepository pointRepository;

    @Test
    @DisplayName("모든 사용자의 포인트 정보를 가져오는 테스트")
    void findAllUsersPointInfoTest() {
        List<PointDto> usersPoint = pointRepository.findAllUserPoint();

        assertThat(usersPoint).isNotNull();
        assertThat(usersPoint.size()).isEqualTo(15);
        assertThat(usersPoint.get(0).getPoint()).isGreaterThan(usersPoint.get(usersPoint.size() - 1).getPoint());

        usersPoint.forEach(System.out::println);
    }

    @Test
    @DisplayName("포인트가 음수인 사용자의 포인트 정보만 가져오는 테스트")
    void findPenaltyUsersPointInfoTest() {
        List<PointDto> penaltyUsers = pointRepository.findAllPenaltyUsers();

        assertThat(penaltyUsers).isNotNull();
        assertThat(penaltyUsers.size()).isZero();
    }

    @Test
    @DisplayName("포인트가 음수인 사용자의 포인트 정보만 가져오는 테스트지, 음수 포인트가 존재할 때")
    void findPenaltyUsersPointInfoTest_Exist() {
        pointRepository.save(new Point(new User("nokchax1"), -100));

        List<PointDto> penaltyUsers = pointRepository.findAllPenaltyUsers();

        assertThat(penaltyUsers).isNotNull();
        assertThat(penaltyUsers.size()).isNotZero();

        penaltyUsers.forEach(System.out::println);
    }

    @Test
    @DisplayName("포인트를 조회하는데 사용자 아이디로 조회하는 경우")
    void findUserPointByUserIds() {
        List<String> userIds = Arrays.asList("nokchax1", "nokchax2", "nokchax3", "nokchax4", "nokchax5");

        List<PointDto> pointDtos = pointRepository.findUserPointByUserId(userIds);

        assertThat(pointDtos).isNotNull();
        assertThat(pointDtos.size()).isEqualTo(userIds.size());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("포인트를 조회하는데 사용자 아이디 리스트가 비거나 널인 경우")
    void findUserPointByUserIds_ButNull(List<String> userIds) {
        List<PointDto> pointDtos = pointRepository.findUserPointByUserId(userIds);

        assertThat(pointDtos).isNotNull();
        assertThat(pointDtos.size()).isZero();
    }
}