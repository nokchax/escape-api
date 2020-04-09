package com.nokchax.escape.command;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CommandMakerTest extends JpaTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("애플리케이션 컨텍스트로 부터 클래스를 가지고 빈을 잘 가져오는지 테스트")
    void reflectionTest() {
        log.info("Get beans start");
        Map<Class<?>, Object> collect = new HashMap<>();
        Stream.of(UserRepository.class, MissionRepository.class, EntryRepository.class, ProblemRepository.class)
                .forEach(clazz -> collect.put(clazz, applicationContext.getBean(clazz)));

        assertThat(collect.size()).isNotZero();
        collect.forEach((k, v) -> System.out.println(k + " : " + v));

        log.info("Get beans end");
    }
}