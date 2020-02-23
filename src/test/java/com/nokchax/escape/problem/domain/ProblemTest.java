package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.repository.ProblemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
@Sql("/problems.sql")
public class ProblemTest {
    @Autowired
    ProblemRepository problemRepository;

    @Test
    public void getAllProblemTest() {
        List<Problem> problems = problemRepository.findAll();

        problems.forEach(System.out::println);
    }
}