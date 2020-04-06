package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class TodoCommand extends Command<MissionService> {

    public TodoCommand(Message message, Map<Class<?>, Object> processors) {
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
