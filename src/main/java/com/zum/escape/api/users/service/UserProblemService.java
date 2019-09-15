package com.zum.escape.api.users.service;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.dto.UserProblemSolveDto;
import com.zum.escape.api.users.repository.UserProblemRepository;
import com.zum.escape.api.util.Command;
import com.zum.escape.api.util.DateTimeMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProblemService {
    private final UserProblemRepository userProblemRepository;
    private final ProblemService problemService;

    public List<UserProblem> findAllSolvedProblemsInThisWeek(User user) {

        return userProblemRepository.findByUserEqualsAndSolvedTimeBetween(
                user,
                DateTimeMaker.getStartOfWeek(),
                DateTimeMaker.getEndOfWeek()
        );
    }

    public List<UserProblemSolveDto> findAllUsersSolvedThisProblem(Command command) {
        if(command.isArgsEmpty())
            return Collections.emptyList();

        Problem problem = problemService.findProblem(command.getFirstArg());
        if (problem == null)
            return Collections.emptyList();

        List<UserProblem> userProblems = userProblemRepository.findByProblemEquals(problem);

        return userProblems.stream()
                .map(UserProblem::getUser)
                .map(User::toUserProblemSolveDto)
                .collect(Collectors.toList());
    }
}
