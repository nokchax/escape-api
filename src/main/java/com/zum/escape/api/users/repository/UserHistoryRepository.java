package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.UserHistory;
import com.zum.escape.api.users.domain.UserHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, UserHistoryId> {
}
