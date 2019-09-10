package com.zum.escape.api.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Immutable
@Subselect(
        "select user_id, count(distinct question_id) as total_count, count(distinct if(difficulty='HARD',question_id,null)) as hard_count, " +
                "count(distinct if(difficulty='MEDIUM',question_id,null)) as medium_count, " +
                "count(distinct if(difficulty='EASY',question_id,null)) as easy_count, " +
                "from user_problem left join problem on (user_problem.problem_id = problem.question_id) " +
                "group by user_id;"
)
@NoArgsConstructor
public class UserProblemHistory {
    @Id
    private String userId;
    private int hardCount;
    private int mediumCount;
    private int easyCount;
    private int totalCount;
}
