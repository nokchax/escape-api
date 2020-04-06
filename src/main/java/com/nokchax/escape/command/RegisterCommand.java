package com.nokchax.escape.command;

import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class RegisterCommand extends Command<UserService>{
    public RegisterCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
        sudo = true;
    }

    // id, pw, name
    @Override
    public String internalProcess() {
        User user = new User(getOptions().get("u"), getOptions().get("p"), getOptions().get("n"));

        processor().registerUser(user);

        return "Register complete";
    }
}
