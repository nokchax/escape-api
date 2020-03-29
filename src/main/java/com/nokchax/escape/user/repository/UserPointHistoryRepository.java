package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.UserPointHistory;
import com.nokchax.escape.user.domain.UserPointHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointHistoryRepository extends JpaRepository<UserPointHistory, UserPointHistoryId>, UserPointHistoryRepositoryCustom {
}
