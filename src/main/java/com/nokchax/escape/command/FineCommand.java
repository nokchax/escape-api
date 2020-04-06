package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class FineCommand extends Command<PointRepository> {

    public FineCommand(Message message, Map<Class<?>, Object> processors) {
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
