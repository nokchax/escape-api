package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

public class HistoryCommand extends Command<ProblemSolveHistoryRepository> {

    public HistoryCommand(Message message, ApplicationContext processors) {
        super(message, processors, Collections.singletonList("u")); //userId
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllHistoryWithOrdering(),
                "No one solved a problem"
        );
    }
}
