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
}