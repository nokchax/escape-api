package com.zum.escape.api.problem.domain.entity;

import com.zum.escape.api.users.domain.UserProblem;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Transient
    public static final String LEETCODE_PROBLEM_URL = "https://leetcode.com/problems/";

    @Id
    @Column(name = "question_id")
    private Long id;
    @Column(name = "front_end_question_id", unique = true)
    private Long viewId;
    private String title;
    @Column(name = "title_slug")
    private String titleSlug;
    private boolean hide;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @OneToMany(mappedBy = "problem")
    private Set<UserProblem> userProblem = new HashSet<>();


    public boolean isHard() {
        return difficulty.isHard();
    }

    public boolean isMedium() {
        return difficulty.isMedium();
    }

    public boolean isEasy() {
        return difficulty.isEasy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return hide == problem.hide &&
                Objects.equals(id, problem.id) &&
                Objects.equals(viewId, problem.viewId) &&
                Objects.equals(title, problem.title) &&
                Objects.equals(titleSlug, problem.titleSlug) &&
                difficulty == problem.difficulty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, viewId, title, titleSlug, hide, difficulty);
    }

    public String leetcodeUrl() {
        return LEETCODE_PROBLEM_URL + this.titleSlug;
    }
}
