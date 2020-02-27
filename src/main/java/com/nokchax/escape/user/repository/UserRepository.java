package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
