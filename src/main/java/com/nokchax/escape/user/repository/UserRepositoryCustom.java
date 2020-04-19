package com.nokchax.escape.user.repository;

import com.nokchax.escape.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    /** 사용자 아이디로 사용자 엔티티 찾기 */
    List<User> findByUserId(String target);
    /** 사용자 아이디로 사용자 엔티티 찾기 */
    Optional<User> findOneByUserId(String target);
    /** 사용자 아이디로 사용자, 푼 문제 엔티티 찾기 */
    List<User> findByUserIdWithSolvedProblems(String target);
    /** 사용자 텔레그램 아이디로 엔티티 찾기 */
    Optional<User> findByTelegramId(String target);
}
