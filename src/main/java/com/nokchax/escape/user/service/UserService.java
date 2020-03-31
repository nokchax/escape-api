package com.nokchax.escape.user.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*
        case 1. update all
        case 2. target is empty -> find by request user's id
        case 2. update target id
     */
    public List<User> findByArgument(UpdateCommand.UpdateArgument argument) {
        if(argument.isEmptyArgument()) {
            return userRepository.findByTelegramId(argument.getRequestUsersTelegramId());
        }

        return userRepository.findByUserId(argument.getTarget());
    }
}
