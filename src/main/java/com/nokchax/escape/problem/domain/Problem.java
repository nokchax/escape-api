package com.nokchax.escape.problem.domain;


import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.problem.dto.ProblemDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@ToString(exclude = {"solvedProblems"})
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    @Column(name = "problem_id")
    private Long id;
    @Column(name = "front_end_problem_id")
    private Long viewId;
    private String title;
    @Column(name = "title_slug")
    private String titleSlug;
    private boolean hide;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY)
    private Set<SolvedProblem> solvedProblems = new HashSet<>();

    public boolean isUpdated(Problem problem) {
        return !problem.viewId.equals(this.viewId) ||
                !problem.title.equals(this.title) ||
                !problem.titleSlug.equals(this.titleSlug) ||
                !problem.difficulty.equals(this.difficulty) ||
                problem.hide != this.hide;
    }

    public boolean isUpdated(CrawledProblemInfo crawledProblemInfo) {
        return !crawledProblemInfo.getFrontendProblemId().equals(this.viewId) ||
                !crawledProblemInfo.getProblemTitle().equals(this.title) ||
                !crawledProblemInfo.getProblemTitleSlug().equals(this.titleSlug) ||
                !crawledProblemInfo.getDifficulty().equals(this.difficulty) ||
                !crawledProblemInfo.getProblemId().equals(this.id) ||
                crawledProblemInfo.isProblemHide() != this.hide;
    }

    public ProblemDto toDto() {
        return ProblemDto.builder()
                .title(title)
                .titleSlug(titleSlug)
                .difficulty(difficulty)
                .viewId(viewId)
                .build();
    }
}
