package com.zum.escape.api.users.service;

import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.CrawledUserInfo;
import com.zum.escape.api.thirdPartyAdapter.leetcode.service.LeetCodeService;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final ProblemService problemService;
    private final UserRepository userRepository;
    private final LeetCodeService leetCodeService;

    @Transactional
    public String addUser(String userId) {
        if(userRepository.existsByUserId(userId))
            return "User already exists";

        CrawledUserInfo crawledUserInfo = getCrawledUserInfo(userId).get();

        userRepository.save(crawledUserInfo.toUser());

        return "Register complete";
    }

    public boolean checkUserSolvedProblem(String userId) {
        User user = userRepository.findByUserId(userId);
        CrawledUserInfo crawledUserInfo = getCrawledUserInfo(userId)
                .orElse(CrawledUserInfo.NOT_UPDATED);

        return user.checkSolveQuestion(crawledUserInfo);
    }


    private Optional<CrawledUserInfo> getCrawledUserInfo(String userId) {
        try {
            CrawledUserInfo crawledUserInfo = leetCodeService.findUser(userId);
            crawledUserInfo.setProblems(problemService.toProblem(crawledUserInfo));
            return Optional.of(crawledUserInfo);
        } catch (IOException e) {
            log.error("Fail to find user");
        }
        return Optional.ofNullable(null);
    }
}
