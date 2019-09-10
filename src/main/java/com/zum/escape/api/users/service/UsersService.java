package com.zum.escape.api.users.service;

import com.zum.escape.api.domain.entity.Problem;
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
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final ProblemService problemService;
    private final UserRepository userRepository;
    private final LeetCodeService leetCodeService;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public String addUser(String userId) {
        User newUser = new User(userId);

        if(userRepository.existsByLeetcodeName(newUser.getLeetcodeName()))
            return "User already exists";

        CrawledUserInfo crawledUserInfo = getCrawledUserInfo(newUser.getLeetcodeName());
        newUser.updateSolvedProblems(crawledUserInfo);

        userRepository.save(newUser);

        return "Register complete";
    }

    public List<Problem> updateUser(User user) {
        CrawledUserInfo crawledUserInfo = getCrawledUserInfo(user.getLeetcodeName());
        if(!user.checkSolveQuestion(crawledUserInfo))
            return Collections.emptyList();

        return user.updateSolvedProblems(crawledUserInfo);
    }


    private CrawledUserInfo getCrawledUserInfo(String userId) {
        try {
            CrawledUserInfo crawledUserInfo = leetCodeService.findUser(userId);
            crawledUserInfo.setProblems(problemService.toProblem(crawledUserInfo));
            return crawledUserInfo;
        } catch (IOException e) {
            log.error("Fail to find user");
        }
        return CrawledUserInfo.NOT_UPDATED;
    }
}
