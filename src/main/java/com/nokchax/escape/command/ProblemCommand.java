package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

// TODO: 2020-03-30 problem name
public class ProblemCommand extends Command<ProblemRepository> {
    public ProblemCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
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
