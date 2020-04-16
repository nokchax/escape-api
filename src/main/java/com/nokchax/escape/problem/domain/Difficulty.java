package com.nokchax.escape.problem.domain;


import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Difficulty {
    EASY(1, 1),
    MEDIUM(2, 2),
    HARD(5,3);

    private final int solvePoint;
    private final int level;

    private static final Map<Integer, Difficulty> DIFFICULTIES;

    static {
        DIFFICULTIES = Arrays.stream(Difficulty.values())
                .collect(Collectors.toMap(Difficulty::getLevel, Function.identity()));
    }

    Difficulty(int solvePoint, int level) {
        this.solvePoint = solvePoint;
        this.level = level;
    }

    public static int countToScore(SolvedProblemSummaryDto solvedProblemSummaryDto) {

        return EASY.solvePoint * solvedProblemSummaryDto.getEasyCount() +
                MEDIUM.solvePoint * solvedProblemSummaryDto.getMediumCount() +
                HARD.solvePoint * solvedProblemSummaryDto.getHardCount();
    }

    public static Difficulty ofLevel(int level) {
        if(!DIFFICULTIES.containsKey(level)) {
            throw new IllegalArgumentException("Invalid level or new level type : " + level);
        }

        return DIFFICULTIES.get(level);
    }
}
