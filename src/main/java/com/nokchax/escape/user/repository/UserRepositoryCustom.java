package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByUserId(String target);
    List<User> findByTelegramId(String target);
}
