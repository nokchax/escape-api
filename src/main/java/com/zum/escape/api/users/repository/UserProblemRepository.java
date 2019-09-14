package com.zum.escape.api.users.repository;

import com.zum.escape.api.domain.entity.Problem;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import com.zum.escape.api.users.domain.UserProblemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserProblemRepository extends JpaRepository<UserProblem, UserProblemId> {
    List<UserProblem> findByUserEqualsAndSolvedTimeBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<UserProblem> findByProblemEquals(Problem problem);
}
