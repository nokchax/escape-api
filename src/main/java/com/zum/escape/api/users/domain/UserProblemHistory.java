package com.zum.escape.api.users.domain;

import com.zum.escape.api.users.dto.ProblemHistoryDto;
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
//@Entity
@ToString
@Immutable
@Subselect(
        "select " +
                "  user_id, " +
                "  count(distinct question_id) as total_count, " +
                "  count(distinct case when difficulty = 'HARD' then question_id else null end) as hard_count, " +
                "  count(distinct case when difficulty = 'MEDIUM' then question_id else null end) as medium_count, " +
                "  count(distinct case when difficulty = 'EASY' then question_id else null end) as easy_count " +
                "from " +
                "  user_problem left join problem " +
                "on (user_problem.problem_id = problem.question_id) " +
                "group by " +
                "  user_id"
)
@NoArgsConstructor
public class UserProblemHistory {
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

    public ProblemHistoryDto toProblemHistoryDto() {
        return ProblemHistoryDto.builder()
                .userId(this.userId)
                .totalCount(this.totalCount)
                .hardCount(this.hardCount)
                .mediumCount(this.mediumCount)
                .easyCount(this.easyCount)
                .build();
    }
}
