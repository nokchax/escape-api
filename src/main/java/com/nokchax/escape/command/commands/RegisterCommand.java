package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"register", "r"})
public class RegisterCommand extends Command<UserService> implements SudoCommand {
    private static final String USER_ID = "u";
    private static final String PASSWORD = "p";
    private static final String NAME = "n";

    public RegisterCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        processor().registerUser(new User(getUserId(), getPassword(), getName()));

        return "Register complete";
    }

    private String getUserId() {
        return getArgument(USER_ID);
    }

    private String getPassword() {
        return getArgument(PASSWORD);
    }

    private String getName() {
        return getArgument(NAME);
    }
}
