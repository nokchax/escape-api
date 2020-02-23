package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @MethodSource
    @ParameterizedTest
    public void updateTest(Problem updatedProblem, boolean result) {
        Problem origin = new Problem(1L, 1L, "1", "1", false, Difficulty.EASY);

        assertThat(origin.checkUpdated(updatedProblem)).isEqualTo(result);
    }

    private static Stream updateTest() {
        return Stream.of(
                Arguments.of(new Problem(1L, 1L, "1", "1", false, Difficulty.EASY), false),
                Arguments.of(new Problem(1L, 2L, "1", "1", false, Difficulty.EASY), true),
                Arguments.of(new Problem(1L, 1L, "2", "1", false, Difficulty.EASY), true),
                Arguments.of(new Problem(1L, 1L, "1", "2", false, Difficulty.EASY), true),
                Arguments.of(new Problem(1L, 1L, "1", "1", true, Difficulty.EASY), true),
                Arguments.of(new Problem(1L, 1L, "1", "1", true, Difficulty.MEDIUM), true)
        );
    }
}