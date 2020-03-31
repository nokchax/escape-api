package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;

public class UpdateProblemCommand extends Command<UpdateService> {

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                getProcessor().updateProblems(),
                "Problem list is up to date"
        );
    }
}
