package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class PointCommand extends Command<PointRepository> {

    public PointCommand(Message message, Map<Class<?>, Object> processors) {
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
