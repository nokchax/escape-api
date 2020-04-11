package com.nokchax.escape.user.domain;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
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
    @Transactional
    @DisplayName("회원이 새문제를 풀었을때 새로푼 문제들이 저장 되는지")
    void persistenceTest() {
        beforeQuery();
        User user = userRepository.findByUserIdWithSolvedProblems("nokchax1").get(0);
        afterQuery();
        Problem problem = problemRepository.findById(123L).orElseThrow(IllegalAccessError::new);

        System.out.println(user);
        System.out.println(user.getSolvedProblem().size());
        Set<SolvedProblem> solvedProblems = new HashSet<>();
        solvedProblems.add(SolvedProblem.builder()
                .mission(new Mission(1))
                .problem(problem)
                .user(user)
                .build());

        beforeQuery();
        user.addSolvedProblems(solvedProblems);
        afterQuery();


        beforeClear();
        entityManager.flush();
        entityManager.clear();
        afterClear();
    }
}