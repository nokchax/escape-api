package com.nokchax.escape.problem.domain;


import com.zum.escape.api.problem.domain.entity.Difficulty;
import com.zum.escape.api.users.domain.UserProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
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
    @Column(name = "front_end_question_id")
    private Long viewId;
    private String title;
    @Column(name = "title_slug")
    private String titleSlug;
    private boolean hide;
    @Enumerated(EnumType.STRING)
    private String difficulty;
//    @OneToMany(mappedBy = "problem")
//    private Set<UserProblem> userProblem = new HashSet<>();
}
