package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zum.escape.api.domain.entity.Problem;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatStatusPair {
    private Stat stat;
    private Difficulty difficulty;

    public Problem toProblem() {
        return Problem.builder()
                .id(stat.getQuestionId())
                .viewId(stat.getFrontendQuestionId())
                .title(stat.getQuestionTitle())
                .titleSlug(stat.getQuestionTitleSlug())
                .hide(stat.isQuestionHide())
                .difficulty(difficulty.levelToDifficulty())
                .build();
    }
}
