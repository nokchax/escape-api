package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.repository.PointRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class PointCommand extends Command<PointRepository> {

    public PointCommand(Message message, ApplicationContext processors) {
        super(message, processors);
        this.clazz = PointRepository.class;
        extractOptions(message.getText());
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().findAllUserPoint(),
                "There is no user"
        );
    }
}
