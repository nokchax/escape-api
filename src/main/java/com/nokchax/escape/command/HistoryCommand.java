package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

/// TODO: 2020-03-30 /history userId -> user's history
public class HistoryCommand extends Command<ProblemSolveHistoryRepository> {
    public HistoryCommand(Message message) {
        super(message);
    }

    @Override
    public String process() {
        return MessageMaker.toMessage(
                getProcessor().findAllHistoryWithOrdering(),
                "No one solved a problem"
        );
    }
}
