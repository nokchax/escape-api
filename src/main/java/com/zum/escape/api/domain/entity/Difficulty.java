package com.zum.escape.api.domain.entity;

import lombok.Getter;

@Getter
public enum Difficulty {
    UNKNOWN(0), EASY(1), MEDIUM(2), HARD(5);

    private int level;

    Difficulty(int level) {
        this.level = level;
    }
}
