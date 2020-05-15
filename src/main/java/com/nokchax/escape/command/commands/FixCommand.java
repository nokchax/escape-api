package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.leetcode.service.UpdateService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"fix"})
public class FixCommand extends Command<UpdateService> implements SudoCommand {

    public FixCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext);
    }

    @Override
    protected String internalProcess() {
        return null;
    }
}
