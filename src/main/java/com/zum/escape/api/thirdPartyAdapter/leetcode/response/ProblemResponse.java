package com.zum.escape.api.thirdPartyAdapter.leetcode.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemResponse {
    @JsonProperty("num_total")
    private Long numTotal;
    @JsonProperty("stat_status_pairs")
    private List<StatStatusPair> statStatusPairs;

    public List<Problem> toProblemList() {
        List<Problem> problems = new ArrayList<>(statStatusPairs.size());

        statStatusPairs.forEach(
                problem -> problems.add(problem.toProblem())
        );

        return problems;
    }

    public List<UserProblem> toUserProblemList(User user) {
        List<UserProblem> userProblems = new ArrayList<>(statStatusPairs.size());
        for(StatStatusPair i : statStatusPairs) {
            if(i.getStatus() != null) {
                userProblems.add(i.toUserProblem(user));
            }
        }
        return userProblems;
    }
}
