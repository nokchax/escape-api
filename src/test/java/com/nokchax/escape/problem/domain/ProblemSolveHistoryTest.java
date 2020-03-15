package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("dev")
class ProblemSolveHistoryTest {
    @Autowired
    private ProblemSolveHistoryRepository problemSolveHistoryRepository;

    /**
     * =========================================================Before find all
     * Hibernate:
     *     select
     *         problemsol0_.user_id as user_id1_0_,
     *         problemsol0_.easy_count as easy_cou2_0_,
     *         problemsol0_.hard_count as hard_cou3_0_,
     *         problemsol0_.medium_count as medium_c4_0_,
     *         problemsol0_.total_count as total_co5_0_
     *     from
     *         ( SELECT
     *             user_id,
     *             COUNT(DISTINCT question_id) AS total_count,
     *             COUNT(DISTINCT CASE
     *                 WHEN difficulty = 'HARD' THEN question_id
     *                 ELSE NULL
     *             END) AS hard_count,
     *             COUNT(DISTINCT CASE
     *                 WHEN difficulty = 'MEDIUM' THEN question_id
     *                 ELSE NULL
     *             END) AS medium_count,
     *             COUNT(DISTINCT CASE
     *                 WHEN difficulty = 'EASY' THEN question_id
     *                 ELSE NULL
     *             END) AS easy_count
     *         FROM
     *             user_problem
     *         LEFT JOIN
     *             problem
     *                 ON (
     *                     user_problem.problem_id = problem.question_id
     *                 )
     *         GROUP BY
     *             user_id ) problemsol0_
     * =========================================================After find all
     *
     * 쿼리 한번
     */
    @Test
    void findAllTest() {
        System.out.println("=========================================================Before find all");
        problemSolveHistoryRepository.findAll();
        System.out.println("=========================================================After find all");
    }
}