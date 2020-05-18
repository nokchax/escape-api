package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.leetcode.service.UpdateService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

@CommandMapping(commands = {"view"})
public class InfoCommand extends Command<UpdateService> implements SudoCommand {
    private static final String USER_ID = "u";

    public InfoCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext, Collections.singletonList(USER_ID));
    }

    @Override
    protected String internalProcess() {
        return processor().viewUserProblem(extractArgument());
    }

    private UpdateCommand.UpdateArgument extractArgument() {
        return UpdateCommand.UpdateArgument.builder()
                .target(getDefaultArgument())
                .requestUsersTelegramId(commander())
                .build();
    }
}
