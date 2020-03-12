package com.nokchax.escape.problem.domain;

import com.nokchax.escape.problem.repository.ProblemRepository;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
@ActiveProfiles("dev")
class SolvedProblemTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;

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


    /**
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

    /**
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

    /**
     * =========================================================Before find by id
     * =========================================================After find by id
     */
    @Test
    void updateTest() {
        User user = new User("nokchax", "123", "광");

        userRepository.saveAndFlush(user);

        System.out.println("=========================================================Before find by id");
        userRepository.findById("nokchax");
        System.out.println("=========================================================After find by id");
    }


    void howToRemoveCachedEntity() {
    }
}