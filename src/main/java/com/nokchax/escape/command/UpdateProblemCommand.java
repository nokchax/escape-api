package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UpdateProblemCommand extends Command<UpdateService> implements SudoCommand {

    public UpdateProblemCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().updateProblems(),
                "Problem list is up to date"
        );
    }
}
