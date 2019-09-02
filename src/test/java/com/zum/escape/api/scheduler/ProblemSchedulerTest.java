package com.zum.escape.api.scheduler;


import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.domain.repository.ProblemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProblemSchedulerTest {
    @Autowired
    private ProblemScheduler problemScheduler;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    public void test() {
        problemScheduler.saveOrUpdateProblems();

        List<Problem> problems = problemRepository.findAll();

        System.out.println(problemRepository.count());
        problems.forEach(System.out::println);
    }

}