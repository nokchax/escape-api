package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
@ActiveProfiles("dev")
class SolvedProblemTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;

    @Test
    void constructorTest() {
        User user = new User("nokchax", "123", "광");

        assertThat(user.getSolvedProblemCount()).isEqualTo(0);
        assertThat(user.getSolvedProblem()).isNotNull();
        assertThat(user.getSolvedProblem().size()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource
    void illegalConstructorTest(String id, String password, String name) {
        assertThatThrownBy(() -> new User(id, password, name)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream illegalConstructorTest() {
        return Stream.of(
                Arguments.of(null, "123", "광"),
                Arguments.of("", "123", "광"),
                Arguments.of("nokchax", null, "광"),
                Arguments.of("nokchax", "", "광"),
                Arguments.of("nokchax", "123", null),
                Arguments.of("nokchax", "123", "")
        );
    }


    /*
     * https://stackoverflow.com/questions/45635827/how-do-i-stop-spring-data-jpa-from-doing-a-select-before-a-save
     * =========================================================Before save
     * Hibernate: save하는 객체가 새 객체인지 DB에 저장된 객체인지 확인하기 위함
     *     select
     *         user0_.id as id1_4_0_,
     *         user0_.name as name2_4_0_,
     *         user0_.password as password3_4_0_,
     *         user0_.solved_problem_count as solved_p4_4_0_
     *     from
     *         users user0_
     *     where
     *         user0_.id=?
     * =========================================================After save
     * =========================================================Before flush
     * Hibernate:
     *     insert
     *     into
     *         users
     *         (name, password, solved_problem_count, id)
     *     values
     *         (?, ?, ?, ?)
     * =========================================================After flush
     */
    @Test
    void saveAndFlushTest() {
        User user = new User("nokchax", "123", "광");

        System.out.println("=========================================================Before save");
        userRepository.save(user);
        System.out.println("=========================================================After save");
        System.out.println("=========================================================Before flush");
        userRepository.flush();
        System.out.println("=========================================================After flush");
    }

    /*
     * =========================================================Before save and flush
     * Hibernate:
     *     select
     *         user0_.id as id1_4_0_,
     *         user0_.name as name2_4_0_,
     *         user0_.password as password3_4_0_,
     *         user0_.solved_problem_count as solved_p4_4_0_
     *     from
     *         users user0_
     *     where
     *         user0_.id=?
     * Hibernate:
     *     insert
     *     into
     *         users
     *         (name, password, solved_problem_count, id)
     *     values
     *         (?, ?, ?, ?)
     * =========================================================After save and flush
     */
    @Test
    void saveAndFlushTest2() {
        User user = new User("nokchax", "123", "광");

        System.out.println("=========================================================Before save and flush");
        userRepository.saveAndFlush(user);
        System.out.println("=========================================================After save and flush");
    }

    /*
     * =========================================================Before find by id
     * =========================================================After find by id
     */
    @Test
    void updateTest() {
        User user = new User("nokchax", "123", "광");

        System.out.println("=========================================================Before save and flush");
        userRepository.saveAndFlush(user);
        System.out.println("=========================================================After save and flush");

        System.out.println("=========================================================Before find by id");
        userRepository.findById("nokchax");
        System.out.println("=========================================================After find by id");
    }


    @Test
    void howToRemoveCachedEntity() {
        User user = new User("nokchax", "123", "광");

        userRepository.saveAndFlush(user);

        System.out.println("=========================================================Before find by id");
        userRepository.findById("nokchax");
        System.out.println("=========================================================After find by id");

        System.out.println("=========================================================Before clear");
        entityManager.clear();
        System.out.println("=========================================================After clear");

        System.out.println("=========================================================Before find by id");
        userRepository.findById("nokchax");
        System.out.println("=========================================================After find by id");
    }

    /*
     * fetch 가 LAZY 일 때
     * =========================================================Before find all
     * Hibernate:
     *     select
     *         user0_.id as id1_4_,
     *         user0_.name as name2_4_,
     *         user0_.password as password3_4_,
     *         user0_.solved_problem_count as solved_p4_4_
     *     from
     *         users user0_
     * =========================================================After find all
     *
     * fetch 가 EAGER 일 때
     * user 테이블 1번 select
     * 각 user마다 query 한번 -> N+1
     * =========================================================Before find all
     * Hibernate:
     *     select
     *         user0_.id as id1_4_,
     *         user0_.name as name2_4_,
     *         user0_.password as password3_4_,
     *         user0_.solved_problem_count as solved_p4_4_
     *     from
     *         users user0_
     * Hibernate: * 5
     *     select
     *         solvedprob0_.user_id as user_id2_3_0_,
     *         solvedprob0_.problem_id as problem_1_3_0_,
     *         solvedprob0_.problem_id as problem_1_3_1_,
     *         solvedprob0_.user_id as user_id2_3_1_,
     *         solvedprob0_.solved_time as solved_t3_3_1_,
     *         problem1_.question_id as question1_0_2_,
     *         problem1_.difficulty as difficul2_0_2_,
     *         problem1_.hide as hide3_0_2_,
     *         problem1_.title as title4_0_2_,
     *         problem1_.title_slug as title_sl5_0_2_,
     *         problem1_.front_end_question_id as front_en6_0_2_
     *     from
     *         user_problem solvedprob0_
     *     inner join
     *         problem problem1_
     *             on solvedprob0_.problem_id=problem1_.question_id
     *     where
     *         solvedprob0_.user_id=?
     * =========================================================After find all
     *
     */
    @Test
    void selectTest() {
        System.out.println("=========================================================Before find all");
        List<User> users = userRepository.findAll();
        System.out.println("=========================================================After find all");

        // lazy 일 때 아래를 수행하면 solvedProblems 를 가져와야 하기 때문에 그때 그때 select query 가 한 번 더 수행됨.
        // users.forEach(System.out::println);
        // 크롤링 하는 시점에서 비교가 필요한건 지금 까지 푼 문제수이기 때문에 단순 조회 시점에는 lazy 로 가져오고,
        // 무조건 업데이트가 되는 시점에는 한번에 solved problems 까지 가져오는게 좋다.
    }

    @Test
    void groupByTest() {
        System.out.println("=========================================================Before find solve problems summary dto");
        List<SolvedProblemSummaryDto> summary = solvedProblemRepository.getSolvedProblemPerUserOfLatestMission();
        System.out.println("=========================================================After find solve problems summary dto");

        summary.forEach(System.out::println);
    }
}