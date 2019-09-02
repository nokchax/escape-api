package com.zum.escape.api.domain.entity;

public enum Difficulty {
    UNKNOWN(0), EASY(1), MEDIUM(2), HARD(3);

    private int level;

    Difficulty(int level) {
        this.level = level;
    }
}
