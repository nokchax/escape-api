package com.nokchax.escape.leetcode.crawl.api.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeetcodeApiResponse {
    @JsonProperty("user_name")
    private String userId;
    @JsonProperty("num_solved")
    private int solvedProblemCount;
    @JsonProperty("num_total")
    private Long totalProblemCount;
    @JsonProperty("stat_status_pairs")
    private List<ProblemInfo> problemInfos;

    public CrawledUserInfo toCrawledUserInfo() {
        return CrawledUserInfo.builder()
                .userId(userId)
                .solvedProblemCount(solvedProblemCount)
                .solvedProblems(
                        problemInfos.stream()
                                .filter(ProblemInfo::isSolvedProblem)
                                .map(ProblemInfo::toProblemSolveInfo)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
