package com.zum.escape.api.users.service;

import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.repository.UserProblemRepository;
import com.zum.escape.api.util.DateTimeMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProblemService {
    private final UserProblemRepository userProblemRepository;

    public List<UserProblem> findAllSolvedProblemsInThisWeek(User user) {

        return userProblemRepository.findByUserEqualsAndSolvedTimeBetween(
                user,
                DateTimeMaker.getStartOfWeek(),
                DateTimeMaker.getEndOfWeek()
        );
    }
}
