package com.zum.escape.api.users.service;

import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.dto.URL;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserProblemCrawlService {
    private static volatile UserProblemCrawlService userProblemCrawlService;
    private OkHttpHelper okHttpHelper;

    public UserProblemCrawlService() {
        okHttpHelper = OkHttpHelper.getSingleton(false);
    }

    public ProblemResponse getUserProblems(User user) throws IOException {
        ProblemResponse problemResponse = new ProblemResponse();
        Response response = okHttpHelper.getSync(URL.PROBLEMS);
        if(response.isSuccessful() && response.body() != null) {
            String responseData = response.body().string();
            problemResponse = okHttpHelper.fromJson(responseData,ProblemResponse.class);
        } else {
            System.out.println("code : " + response.code() + " message : " + response.message());
        }
        response.close();
        return problemResponse;
    }
}
