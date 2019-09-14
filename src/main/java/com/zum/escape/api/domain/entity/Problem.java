package com.zum.escape.api.domain.entity;

import com.zum.escape.api.users.domain.UserProblem;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "front_end_question_id", unique = true)
    private Long viewId;
    private String status;
    private String title;
    @Column(name = "title_slug")
    private String titleSlug;
    private boolean hide;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @OneToMany(mappedBy = "problem")
    private Set<UserProblem> userProblem = new HashSet<>();
}
