package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ListCommand extends Command<MissionService> {

    public ListCommand(Message message, ApplicationContext processors) {
        super(message, processors);
        this.clazz = MissionService.class;
        extractOptions(message.getText());
    }

    @Override
    public String internalProcess() {
        MissionService processor = processor();

        return MessageMaker.toMessage(
                processor.getAllUserInLatestMission(),
                "No users"
        );
    }
}
