package com.nokchax.escape.user.domain;

import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@ActiveProfiles("dev")
class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/users.sql")
    public void findAllUsersTest() {
        List<User> users = userRepository.findAll();

        users.forEach(System.out::println);
    }
}