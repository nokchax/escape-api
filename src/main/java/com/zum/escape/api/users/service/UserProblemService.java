package com.zum.escape.api.users.service;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.dto.SolvedProblemDto;
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

    public SolvedProblemDto findAllUsersSolvedThisProblem(Command command) {
        if(command.isArgsEmpty())
            return SolvedProblemDto.builder()
                    .problem(null)
                    .userProblemSolveDto(Collections.emptyList())
                    .build();


        Problem problem = problemService.findProblem(Long.parseLong(command.getFirstArg()));
        if (problem == null)
            return SolvedProblemDto.builder()
                    .problem(null)
                    .userProblemSolveDto(Collections.emptyList())
                    .build();

        List<UserProblem> userProblems = userProblemRepository.findByProblemEquals(problem);

        List<UserProblemSolveDto> userProblemSolveDtos = userProblems.stream()
                .map(UserProblem::getUser)
                .map(User::toUserProblemSolveDto)
                .collect(Collectors.toList());

        return SolvedProblemDto.builder()
                .problem(problem)
                .userProblemSolveDto(userProblemSolveDtos)
                .build();
    }
}
