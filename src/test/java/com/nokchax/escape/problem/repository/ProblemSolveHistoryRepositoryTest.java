package com.nokchax.escape.problem.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.problem.dto.ProblemSolveHistoryDto;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProblemSolveHistoryRepositoryTest extends JpaTest {
    @Autowired
    private ProblemSolveHistoryRepository problemSolveHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("사용자들이 푼 총 문제 수를 리턴한 값이 많이 푼 사용자부터 내림차순으로 보여주는지 테스트")
    void findProblemSolveHistoryTest() {
        userRepository.save(new User("newUser"));
        entityManager.flush();

        beforeQuery();
        List<ProblemSolveHistoryDto> histories = problemSolveHistoryRepository.findAllHistoryWithOrdering();
        afterQuery();

        assertThat(histories.size()).isEqualTo(16);
        IntStream.range(1, histories.size())
                .forEach(i ->
                        assertThat(histories.get(i - 1).getTotalCount())
                                .isGreaterThanOrEqualTo(histories.get(i).getTotalCount())
                );

        showResult();
        histories.forEach(System.out::println);
    }
}