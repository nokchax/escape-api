package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

/// TODO: 2020-03-30 /history userId -> user's history
public class HistoryCommand extends Command<ProblemSolveHistoryRepository> {
    public HistoryCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
        this.defaultArgumentAlias = "u"; //user
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllHistoryWithOrdering(),
                "No one solved a problem"
        );
    }
}
