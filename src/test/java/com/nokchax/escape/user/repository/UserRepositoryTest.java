package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {"all", "nokchax1"})
    void findByArgumentTest(String argument) {
        List<User> users = userRepository.findByUserId(argument);

        if(argument.equals("all")) {
            assertThat(users.size()).isGreaterThan(1);
        } else {
            assertThat(users.size()).isEqualTo(1);
        }

        System.out.println("Users size : " + users.size());
        users.forEach(System.out::println);
    }

}