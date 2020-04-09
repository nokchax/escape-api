package com.nokchax.escape.user.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.user.domain.User;
import org.hibernate.annotations.Source;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRepositoryTest extends JpaTest {
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource(value = {"nokchax1, 1", "all, 15"})
    @DisplayName("사용자 이름에 맞는 사용자 엔티티 조회 테스트(all인 경우 모든 사용자를 리턴)")
    void findByUserIdTest(String userId, int expectedSize) {
        beforeQuery();
        List<User> users = userRepository.findByUserId(userId);
        afterQuery();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(expectedSize);

        showResult();
        users.forEach(System.out::println);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 이름에 맞는 사용자 엔티티 조회 테스트(아이디가 null이거나 비어있는 경우)")
    void findByUserIdTest_WithNullOrEmptyString(String userId) {
        beforeQuery();
        List<User> users = userRepository.findByUserId(userId);
        afterQuery();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(0);

        showResult();
        users.forEach(System.out::println);
    }

    @Test
    @DisplayName("텔레그램 아이디로 사용자 찾기 테스트")
    void findByTelegramIdTest() {
        beforeQuery();
        User user = userRepository.findByTelegramId("telegram1")
                .orElseThrow(NullPointerException::new);
        afterQuery();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("nokchax1");

        showResult();
        System.out.println(user);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = ("not exist id value"))
    @DisplayName("DB상에는 존재하지 않는 텔레그램 아이디로 사용자 찾기 테스트")
    void findByTelegramIdTestWithExceptionExpected(String telegramId) {
        beforeQuery();
        assertThatThrownBy(() -> userRepository.findByTelegramId(telegramId).orElseThrow(NullPointerException::new))
                .isInstanceOf(NullPointerException.class);
        afterQuery();
    }

}