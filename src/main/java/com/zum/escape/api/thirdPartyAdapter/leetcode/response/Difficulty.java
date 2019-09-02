package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Difficulty {
    private int level;

    public com.zum.escape.api.domain.entity.Difficulty levelToDifficulty() {
        switch (this.level) {
            case 1:
                return com.zum.escape.api.domain.entity.Difficulty.EASY;
            case 2:
                return com.zum.escape.api.domain.entity.Difficulty.MEDIUM;
            case 3:
                return com.zum.escape.api.domain.entity.Difficulty.HARD;
        }
        return com.zum.escape.api.domain.entity.Difficulty.UNKNOWN;
    }
}
