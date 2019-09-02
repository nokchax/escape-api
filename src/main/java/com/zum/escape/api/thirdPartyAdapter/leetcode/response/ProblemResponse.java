package com.zum.escape.api.thirdPartyAdapter.leetcode.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zum.escape.api.domain.entity.Problem;
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
}
