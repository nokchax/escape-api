package com.zum.escape.api.problem.domain.entity;

import lombok.Getter;

@Getter
public enum Difficulty {
    UNKNOWN(0), EASY(1), MEDIUM(2), HARD(5);

    private int level;

    Difficulty(int level) {
        this.level = level;
    }

    public boolean isHard() {
        return this == HARD;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isEasy() {
        return this == EASY;
    }

    public boolean isEqualTo(Difficulty difficulty) {
        return this == difficulty;
    }
}
