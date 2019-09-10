package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.UserProblemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemHistoryRepository extends JpaRepository<UserProblemHistory, String> {
}
