package com.nokchax.escape;

import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("filedb")
public class FileDbTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void fileCreationTest() {
        List<User> all = userRepository.findAll();
        System.out.println(all.size());


        List<Problem> all1 = problemRepository.findAll();
        all1.forEach(System.out::println);
    }
}
