package com.nokchax.escape.command;

import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RegisterCommand extends Command<UserService> implements SudoCommand {

    public RegisterCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        User user = new User(getOptions().get("u"), getOptions().get("p"), getOptions().get("n"));

        processor().registerUser(user);

        return "Register complete";
    }
}
