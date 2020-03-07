package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("dev")
class SolvedProblemTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void testDDLQuery() {

    }
}