package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatStatusPair {
    private Stat stat;
    private String status;
    private Difficulty difficulty;

    public boolean solved() {
        return "ac".equalsIgnoreCase(status);
    }

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

    public UserProblem toUserProblem(User user, LocalDateTime time) {
        if("ac".equalsIgnoreCase(status))
            return null;
        else
            return UserProblem.builder()
                    .user(user)
                    .problem(toProblem())
                    .solvedTime(time)
                    .build();
    }
}
