package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class UpdateProblemCommand extends Command<UpdateService> {
    public UpdateProblemCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
        this.sudo = true;
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().updateProblems(),
                "Problem list is up to date"
        );
    }
}
