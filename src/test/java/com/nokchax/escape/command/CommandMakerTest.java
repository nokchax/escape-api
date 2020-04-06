package com.nokchax.escape.command;

import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@DataJpaTest
class CommandMakerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void reflectionTest() {
        log.info("Get field start");
        Map<Class<?>, Object> collect = new HashMap<>();
        Stream.of(UserRepository.class, MissionRepository.class, EntryRepository.class, ProblemRepository.class)
                .forEach(clazz -> collect.put(clazz, applicationContext.getBean(clazz)));

        collect.forEach((k, v) -> System.out.println(k + " : " + v));

        log.info("Get field end");
    }
}