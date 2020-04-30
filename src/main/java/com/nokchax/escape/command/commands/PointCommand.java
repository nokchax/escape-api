package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"point", "po"})
public class PointCommand extends Command<PointRepository> {

    public PointCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllUserPoint(),
                "There is no user"
        );
    }
}
