package com.nokchax.escape.problem.dto;

import com.nokchax.escape.problem.domain.Difficulty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemDto {
    private static final String LEETCODE_PROBLEM_URL = "https://leetcode.com/problems/";

    private Long viewId;
    private String title;
    private String titleSlug;
    private Difficulty difficulty;

    @QueryProjection
    public ProblemDto(Long viewId, String title, String titleSlug, Difficulty difficulty) {
        this.viewId = viewId;
        this.title = title;
        this.titleSlug = titleSlug;
        this.difficulty = difficulty;
    }

    public String getLeetcodeProblemUrl() {
        return LEETCODE_PROBLEM_URL + this.titleSlug;
    }
}
