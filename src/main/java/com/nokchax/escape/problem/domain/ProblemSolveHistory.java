package com.nokchax.escape.problem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@ToString
@Immutable
@Subselect(
                "SELECT " +
                "  user_id, " +
                "  COUNT(DISTINCT sp.problem_id) AS total_count, " +
                "  COUNT(DISTINCT CASE WHEN difficulty = 'HARD' THEN sp.problem_id ELSE NULL END) AS hard_count, " +
                "  COUNT(DISTINCT CASE WHEN difficulty = 'MEDIUM' THEN sp.problem_id ELSE NULL END) AS medium_count, " +
                "  COUNT(DISTINCT CASE WHEN difficulty = 'EASY' THEN sp.problem_id ELSE NULL END) AS easy_count " +
                "FROM " +
                "  solved_problem sp LEFT JOIN problem p " +
                "ON (sp.problem_id = p.problem_id) " +
                "GROUP BY " +
                "  user_id"
)
@NoArgsConstructor
public class ProblemSolveHistory {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "total_count")
    private int totalCount;
    @Column(name = "hard_count")
    private int hardCount;
    @Column(name = "medium_count")
    private int mediumCount;
    @Column(name = "easy_count")
    private int easyCount;
}
