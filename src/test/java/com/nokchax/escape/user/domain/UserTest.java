package com.nokchax.escape.user.domain;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import com.zum.escape.api.users.dto.SolvedProblemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
class UserTest extends ServiceLayerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void findAllUsersTest() {
        List<User> users = userRepository.findAll();

        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    void persistenceTest() {
        log.info("Before find");
        User user = userRepository.findById("nokchax1").orElseThrow(IllegalArgumentException::new);
        log.info("After find");
        Problem problem = problemRepository.findById(123L).orElseThrow(IllegalAccessError::new);

        System.out.println(user);
        System.out.println(user.getSolvedProblem().size());
        Set<SolvedProblem> solvedProblems = new HashSet<>();
        solvedProblems.add(SolvedProblem.builder()
                .mission(new Mission(1))
                .problem(problem)
                .user(user)
                .build());

        log.info("Before add");
        user.addSolvedProblems(solvedProblems);
        log.info("After add");


        log.info("Before flush");
        entityManager.flush();
        entityManager.clear();
        log.info("After flush ");
    }
}