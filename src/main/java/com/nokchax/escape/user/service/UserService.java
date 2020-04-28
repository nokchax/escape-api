package com.nokchax.escape.user.service;

import com.nokchax.escape.command.commands.RegisterTelegramIdCommand;
import com.nokchax.escape.command.commands.UpdateCommand;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@Transactional
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
            return findByTelegramId(argument);
        }

        return findByUserId(argument);
    }

    private List<User> findByUserId(UpdateCommand.UpdateArgument argument) {
        List<User> users = userRepository.findByUserId(argument.getTarget());

        if(users.isEmpty()) {
            throw new UserNotFoundException(argument.getTarget());
        }

        return users;
    }

    private List<User> findByTelegramId(UpdateCommand.UpdateArgument argument) {
        User user =  userRepository.findByTelegramId(argument.getRequestUsersTelegramId())
                .orElseThrow(() -> new UserNotFoundException(argument.getRequestUsersTelegramId()));

        return Collections.singletonList(user);
    }

    /** 유저 하나를 랜덤으로 리턴한다 */
    public User findRandomUser() {
        List<User> users = userRepository.findAll();

        if(CollectionUtils.isEmpty(users)) {
            throw new UserNotFoundException("There are no users in db");
        }

        return users.get(ThreadLocalRandom.current().nextInt(users.size()));
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public void updateTelegramId(RegisterTelegramIdCommand.UpdateTelegramIdArgument updateTelegramIdArgument) {
        User user = userRepository.findOneByUserId(updateTelegramIdArgument.getUserId())
                .orElseThrow(() -> new UserNotFoundException(updateTelegramIdArgument.getUserId()));

        user.updateTelegramId(updateTelegramIdArgument);
    }
}
