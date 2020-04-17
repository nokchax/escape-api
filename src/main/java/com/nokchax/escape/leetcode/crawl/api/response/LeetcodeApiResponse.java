package com.nokchax.escape.leetcode.crawl.api.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
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
                .solvedProblems(extractSolvedProblemOnly())
                .build();
    }

    private Set<ProblemSolveInfo> extractSolvedProblemOnly() {
        return problemInfos.stream()
                .filter(ProblemInfo::isSolvedProblem)
                .map(ProblemInfo::toProblemSolveInfo)
                .collect(Collectors.toSet());
    }

    public List<CrawledProblemInfo> toCrawledProblemInfo() {
        return problemInfos.stream()
                .map(ProblemInfo::toCrawledProblemInfo)
                .collect(Collectors.toList());
    }

    public boolean isValidCrawl(String userId) {
        if(StringUtils.isEmpty(this.userId)) {
            log.error("UserID is [{}]", this.userId);
            return false;
        }

        return this.userId.equalsIgnoreCase(userId);
    }
}
