package com.nokchax.escape.problem.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import com.nokchax.escape.problem.domain.Difficulty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ProblemDto extends MessageTemplate {
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

    @Override
    public String title() {
        return "Updated problems";
    }

    @Override
    public String body() {
        return String.format("%4d|%15s\n", viewId, getShortenTitle());
    }

    private String getShortenTitle() {
        return title.length() > 15 ? title.substring(0, 16) : title;
    }
}
