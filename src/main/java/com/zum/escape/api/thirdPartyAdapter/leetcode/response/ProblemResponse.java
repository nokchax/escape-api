package com.zum.escape.api.thirdPartyAdapter.leetcode.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> toProblemNames() {
        return statStatusPairs.stream()
                .filter(StatStatusPair::solved)
                .map(StatStatusPair::getStat)
                .map(Stat::getQuestionTitle)
                .collect(Collectors.toList());
    }

    public List<UserProblem> toUserProblemList(User user) {
        LocalDateTime past = LocalDateTime.of(2010, 1, 1, 0, 0);

        List<UserProblem> userProblems = new ArrayList<>(statStatusPairs.size());
        for(StatStatusPair i : statStatusPairs) {
            if(i.getStatus() != null) {
                userProblems.add(i.toUserProblem(user, past));
            }
        }
        return userProblems;
    }
}
