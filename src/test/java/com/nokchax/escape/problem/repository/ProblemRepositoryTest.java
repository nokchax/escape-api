package com.nokchax.escape.problem.repository;


import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("dev")
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;

    @ParameterizedTest
    @ValueSource(longs = {1, 1281})
    public void findProblemSolveUserTest(Long problemNo) {
        System.out.println("=========================================================Before find");
        List<ProblemSolveUserDto> problemSolveUser = problemRepository.findProblemSolveUser(problemNo);
        System.out.println("=========================================================After find");

        problemSolveUser.forEach(System.out::println);
    }

}