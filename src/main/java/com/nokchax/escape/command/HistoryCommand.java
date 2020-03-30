package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;

public class HistoryCommand extends Command<ProblemSolveHistoryRepository> {
    @Override
    public String process() {
        return MessageMaker.toMessage(
                getProcessor().findAllHistoryWithOrdering(),
                "No one solved a problem"
        );
    }
}
