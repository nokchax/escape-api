package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.point.repository.PointRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

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
