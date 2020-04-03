package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UpdateProblemCommand extends Command<UpdateService> {
    public UpdateProblemCommand(Message message) {
        super(message);
        this.sudo = true;
    }

    @Override
    public String internalProcess() throws Exception {
        return MessageMaker.toMessage(
                processor().updateProblems(),
                "Problem list is up to date"
        );
    }
}
