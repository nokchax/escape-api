package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
}
