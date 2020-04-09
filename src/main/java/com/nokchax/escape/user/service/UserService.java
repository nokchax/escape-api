package com.nokchax.escape.user.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*
        case 1. update all
        case 2. target is empty -> find by request user's id
        case 3. update target id
     */
    public List<User> findByArgument(UpdateCommand.UpdateArgument argument) {
        if(argument.isEmptyArgument()) {
            User user =  userRepository.findByTelegramId(argument.getRequestUsersTelegramId())
                    .orElseThrow(() -> new UserNotFoundException(argument.getRequestUsersTelegramId()));

            return Collections.singletonList(user);
        }

        return userRepository.findByUserId(argument.getTarget());
    }

    /** 유저 하나를 랜덤으로 리턴한다 */
    public User findRandomUser() {
        List<User> users = userRepository.findAll();

        return users.get(ThreadLocalRandom.current().nextInt(users.size()));
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
