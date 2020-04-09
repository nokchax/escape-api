package com.nokchax.escape.problem.domain;


import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
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

    public static int countToScore(SolvedProblemSummaryDto solvedProblemSummaryDto) {

        return EASY.solvePoint * solvedProblemSummaryDto.getEasyCount() +
                MEDIUM.solvePoint * solvedProblemSummaryDto.getMediumCount() +
                HARD.solvePoint * solvedProblemSummaryDto.getHardCount();
    }

    // TODO: 2020-04-03 map으로 변경하기
    public static Difficulty ofLevel(int level) {
        return Difficulty.values()[level - 1];
    }
}
