package com.zum.escape.api.users.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserProblemCrawlService {

    public ProblemResponse getUserProblems(User user) throws IOException {
        //UserLogin userLogin = new UserLogin(user);
        //userLogin.doLogin();

        ProblemResponse problemResponse = new ProblemResponse();
        //Response response = userLogin.getResponse();
        return problemResponse;
    }
}
