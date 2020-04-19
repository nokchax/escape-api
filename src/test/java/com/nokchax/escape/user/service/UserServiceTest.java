package com.nokchax.escape.user.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.command.RegisterTelegramIdCommand;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class UserServiceTest extends ServiceLayerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("Telegram id 업데이트 테스트")
    void telegramIdUpdateTest() {
        User userBeforeUpdate = userRepository.findOneByUserId("nokchax3")
                .orElseThrow(RuntimeException::new);

        assertThat(userBeforeUpdate).isNotNull();
        assertThat(userBeforeUpdate.getTelegramId()).isNull();

        beforeQuery();
        userService.updateTelegramId(RegisterTelegramIdCommand.UpdateTelegramIdArgument
                .builder()
                .userId("nokchax3")
                .telegramId("something")
                .build()
        );
        afterQuery();

        beforeClear();
        entityManager.flush();
        entityManager.clear();
        afterClear();

        User userAfterUpdate = userRepository.findOneByUserId("nokchax3")
                .orElseThrow(RuntimeException::new);

        assertThat(userAfterUpdate).isNotNull();
        assertThat(userAfterUpdate.getTelegramId()).isNotNull();
    }
}