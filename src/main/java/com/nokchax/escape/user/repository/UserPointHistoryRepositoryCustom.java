package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.dto.UserPointDto;

import java.util.List;

public interface UserPointHistoryRepositoryCustom {
    List<UserPointDto> findAllUserPointHistoryWithOrdering();
}
