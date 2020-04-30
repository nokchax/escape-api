package com.nokchax.escape.point.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.command.commands.GivePointCommand;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.point.repository.PointRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PointServiceTest extends ServiceLayerTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private EntityManager entityManager;

    @ParameterizedTest
    @ValueSource(strings = {"all", "nokchax1"})
    @DisplayName("특정, 모든 사용자에게 포인트 줬을때 올바르게 추가 되어 있는지")
    void givePointToAllTest(String targetUser) {
        int additionalPoint = 10;
        Map<String, Integer> beforeUpdate = pointRepository.findAllUserPoint()
                .stream()
                .collect(Collectors.toMap(PointDto::getUserId, PointDto::getPoint));

        entityManager.clear();

        beforeQuery();
        List<PointDto> afterUpdate = pointService.givePointTo(new GivePointCommand.PointArgument(targetUser, Integer.toString(additionalPoint)));
        afterQuery();

        afterUpdate.forEach(pointDto -> {
            Integer beforePoint = beforeUpdate.get(pointDto.getUserId());
            assertThat(beforePoint).isNotNull();
            assertThat(beforePoint).isEqualTo(pointDto.getPoint() - additionalPoint);
        });
    }
}