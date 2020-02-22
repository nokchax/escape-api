package com.nokchax.escape.problem.domain;


import lombok.Getter;

@Getter
public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(5);

    private int solvePoint;

    Difficulty(int solvePoint) {
        this.solvePoint = solvePoint;
    }
}
