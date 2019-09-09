package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
    List<UserProblem> findByUserEqualsAndSolvedDateTimeBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
