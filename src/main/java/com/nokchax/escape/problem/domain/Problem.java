package com.nokchax.escape.problem.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    @Column(name = "question_id")
    private Long id;
    @Column(name = "front_end_question_id")
    private Long viewId;
    private String title;
    @Column(name = "title_slug")
    private String titleSlug;
    private boolean hide;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
//    @OneToMany(mappedBy = "problem")
//    private Set<UserProblem> userProblem = new HashSet<>();

    public boolean checkUpdated(Problem problem) {
        return !problem.viewId.equals(this.viewId) ||
                !problem.title.equals(this.title) ||
                !problem.titleSlug.equals(this.titleSlug) ||
                !problem.difficulty.equals(this.difficulty) ||
                problem.hide != this.hide;
    }
}
