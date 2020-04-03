package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

// TODO: 2020-03-30 problem name
public class ProblemCommand extends Command<ProblemRepository> {
    public ProblemCommand(Message message) {
        super(message);
        this.defaultArgumentAlias = "n";//number
    }

    @Override
    public String internalProcess() {

        return MessageMaker.toMessage(
                processor().findProblemSolveUser(Long.parseLong(getDefaultArgument())),
                "Problem not exist"
        );
    }
}
