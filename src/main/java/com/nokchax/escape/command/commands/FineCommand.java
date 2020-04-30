package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"fine", "f"})
public class FineCommand extends Command<PointRepository> {

    public FineCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllPenaltyUsers(),
                "There is no penalty user"
        );
    }
}
