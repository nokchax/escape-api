package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    User findByUserId(String userId);
}
