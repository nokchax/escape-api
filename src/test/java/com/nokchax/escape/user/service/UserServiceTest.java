package com.nokchax.escape.user.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class UserServiceTest extends ServiceLayerTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("텔레그램 ID로 조회했는데 null이거나 빈 스트링인 경우")
    void whenTelegramIdIsNullOrEmptyTest(String telegramId) {
        //when
        given(userRepository.findByTelegramId(null)).willReturn(Optional.empty());
        given(userRepository.findByTelegramId("")).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByArgument(
                UpdateCommand.UpdateArgument.builder()
                        .requestUsersTelegramId(telegramId)
                        .build()
                )
        ).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("텔레그램 아이디로 조회")
    void whenTelegramIdIsValidTest() {
        given(userRepository.findByTelegramId("1234")).willReturn(Optional.of(new User("nokchax1")));

        List<User> users = userService.findByArgument(UpdateCommand.UpdateArgument.builder().requestUsersTelegramId("1234").build());
        assertThat(users).isNotNull();
        assertThat(users.size()).isOne();
        assertThat(users.get(0).getId()).isEqualTo("nokchax1");
    }

    @Test
    @DisplayName("유저 아이디로 조회")
    void whenUserIdIsValid() {
        given(userRepository.findByUserId("nokchax1")).willReturn(Collections.singletonList(new User("nokchax1")));

        List<User> users = userService.findByArgument(UpdateCommand.UpdateArgument.builder().target("nokchax1").build());

        assertThat(users).isNotNull();
        assertThat(users.size()).isOne();
        assertThat(users.get(0).getId()).isEqualTo("nokchax1");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저 아이디로 조회하는데 null이거나 빈 문자열일 경우")
    void whenUserIdIsInValid(String userId) {
        assertThatThrownBy(() -> userService.findByArgument(UpdateCommand.UpdateArgument.builder().target(userId).build()))
                .isInstanceOf(UserNotFoundException.class);
        log.info("Before verify");
        verify(userRepository, times(1)).findByTelegramId(any());
        verify(userRepository, times(0)).findByUserId(any());
        log.info("After verify");
    }

    @Test
    @DisplayName("유저 아이디로 조회하는데 없는 경우")
    void whenUserIdIsNotExist() {
        assertThatThrownBy(() -> userService.findByArgument(UpdateCommand.UpdateArgument.builder().target("blah blah").build()))
                .isInstanceOf(UserNotFoundException.class);

        log.info("Before verify");
        verify(userRepository).findByUserId(any());
        verify(userRepository, times(0)).findByTelegramId(any());
        log.info("After verify");
    }

    @Test
    @DisplayName("유저 아이디로 조회하는데 있는 경우")
    void whenUserIdIsExist() {
        given(userRepository.findByUserId(anyString())).willReturn(Collections.singletonList(new User("nokchax1")));

        List<User> users = userService.findByArgument(UpdateCommand.UpdateArgument.builder().target("nokchax1").build());

        assertThat(users).isNotNull();
        assertThat(users.size()).isOne();
        assertThat(users.get(0).getId()).isEqualTo("nokchax1");

        log.info("Before verify");
        verify(userRepository).findByUserId(any());
        verify(userRepository, times(0)).findByTelegramId(any());
        log.info("After verify");
    }

    @Test
    @DisplayName("all로 조회하는 경우")
    void whenUserIdIsAll() {
        given(userRepository.findByUserId("all")).willReturn(Arrays.asList(new User("nokchax1"), new User("nokchax2")));

        List<User> users = userService.findByArgument(UpdateCommand.UpdateArgument.UPDATE_ALL);

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);

        log.info("Before verify");
        verify(userRepository).findByUserId(any());
        verify(userRepository, times(0)).findByTelegramId(any());
        log.info("After verify");
    }

    @Test
    @DisplayName("DB에 유저가 한명도 없는 경우")
    void whenUserNotExistsInDB() {
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        assertThatThrownBy(() -> userService.findRandomUser()).isInstanceOf(UserNotFoundException.class);
    }

    // 랜덤 테스트는 귀찮은데.. ThreadLocal.random은 어떻게 테스트해야할까... interface를 둬서 하긴 너무 귀찮고...
}