package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

public class HistoryCommand extends Command<ProblemSolveHistoryRepository> {
    private static final String USER_ID = "u";

    public HistoryCommand(Message message, ApplicationContext processors) {
        super(message, processors, Collections.singletonList(USER_ID)); //userId
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllHistoryWithOrdering(),
                "No one solved a problem"
        );
    }
}
