package com.nokchax.escape.problem.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProblemSolveUserDto extends MessageTemplate {
    private String userId;
    private ProblemDto problem;
    private LocalDateTime solvedTime;
    private LocalDateTime updatedTime;

    @QueryProjection
    public ProblemSolveUserDto(ProblemDto problem, String userId, LocalDateTime solvedTime, LocalDateTime updatedTime) {
        this.problem = problem;
        this.userId = userId;
        this.solvedTime = solvedTime;
        this.updatedTime = updatedTime;
    }

    @Override
    public String liner() {
        return "------------\n";
    }

    @Override
    public String title() {
        return problem.getDifficulty() + "\n"
                + "[" + problem.getTitle() + "]\n"
                + problem.getLeetcodeProblemUrl() + "\n";
    }

    @Override
    public String body() {
        return String.format("%12s\n", userId);
    }
}
