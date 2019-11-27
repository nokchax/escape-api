package com.zum.escape.api.problem.domain.entity;

import com.zum.escape.api.users.domain.UserProblem;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
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
        return this.difficulty.isEqualTo(Difficulty.HARD);
    }

    public boolean isMedium() {
        return this.difficulty.isEqualTo(Difficulty.MEDIUM);
    }

    public boolean isEasy() {
        return this.difficulty.isEqualTo(Difficulty.EASY);
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

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", viewId=" + viewId +
                ", title='" + title + '\'' +
                ", titleSlug='" + titleSlug + '\'' +
                ", hide=" + hide +
                ", difficulty=" + difficulty +
                '}';
    }

    public String problemUrl() {
        return "https://leetcode.com/problems/" + this.titleSlug;
    }
}
