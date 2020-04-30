package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TodoCommand extends Command<MissionService> {

    public TodoCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().getAllMissioningUserInLatestMission(),
                "Every one finished mission"
        );
    }
}
