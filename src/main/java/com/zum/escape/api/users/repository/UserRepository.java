package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByLeetcodeName(String userId);
    User findByLeetcodeName(String userId);
}
