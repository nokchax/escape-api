package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.telegram.telegrambots.meta.api.objects.Message;

public class PointCommand extends Command<PointRepository> {

    public PointCommand(Message message) {
        super(message);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                getProcessor().findAllUserPoint(),
                "There is no user"
        );
    }
}
