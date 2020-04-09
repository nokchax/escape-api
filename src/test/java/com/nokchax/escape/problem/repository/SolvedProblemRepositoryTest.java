package com.nokchax.escape.problem.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolvedProblemRepositoryTest extends JpaTest {
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;

    @Test
    void test() {
        beforeQuery();
        List<SolvedProblemSummaryDto> solvedProblems =
                solvedProblemRepository.findSolvedProblemOfLatestMissionByUserId(Collections.singletonList("nokchax1"));
        afterQuery();

        solvedProblems.forEach(
                solvedProblemSummaryDto -> {
                    assertThat(solvedProblemSummaryDto.getMissionId()).isEqualTo(29);
                    assertThat(solvedProblemSummaryDto.getHardCount()).isEqualTo(0);
                    assertThat(solvedProblemSummaryDto.getMediumCount()).isEqualTo(0);
                    assertThat(solvedProblemSummaryDto.getEasyCount()).isEqualTo(0);
                    assertThat(solvedProblemSummaryDto.evaluateScore()).isEqualTo(0);
                }
        );

        showResult();
        solvedProblems.forEach(System.out::println);
    }

}