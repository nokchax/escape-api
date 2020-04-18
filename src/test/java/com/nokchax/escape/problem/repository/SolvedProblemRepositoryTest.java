package com.nokchax.escape.problem.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SolvedProblemRepositoryTest extends JpaTest {
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;

    @Test
    @DisplayName("최신 미션을 수행중인 사용자를 넘겼을때 해당 사용자가 이번 미션동안 푼 문제수를 제대로 리턴하는지 테스트")
    void findSolvedProblemOfLatestMissionByUserIdTest() {
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


    @ParameterizedTest
    @MethodSource
    @NullAndEmptySource
    @DisplayName("최신 미션을 수행중이지 않은 사용자를 넘겼을때 리턴을 제대로 하는지 테스트")
    void findSolvedProblemOfLatestMissionByUserIdTestWithExceptionableParams(List<String> userIds) {
        beforeQuery();
        List<SolvedProblemSummaryDto> solvedProblems =
                solvedProblemRepository.findSolvedProblemOfLatestMissionByUserId(userIds);
        afterQuery();

        assertThat(solvedProblems).isNotNull();
        assertThat(solvedProblems.size()).isZero();
    }

    private static Stream<Arguments> findSolvedProblemOfLatestMissionByUserIdTestWithExceptionableParams() {
        return Stream.of(
                Arguments.of(Arrays.asList("unacceptable", "no way!")),
                Arguments.of(Collections.singletonList("unacceptable"))
        );
    }

    @Test
    @DisplayName("사용자가 푼 문제를 사용자 id로 조회")
    void findSolvedProblemsByUserId() {
        beforeQuery();
        List<SolvedProblem> solvedProblems = solvedProblemRepository.findSolvedProblemsByUserId("nokchax3");
        afterQuery();

        assertThat(solvedProblems).isNotNull();
        assertThat(solvedProblems.size()).isNotZero();

        showResult();
        log.info("{}", solvedProblems.size());
    }
}