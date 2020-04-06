package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class DoneCommand extends Command<MissionService> {
    public DoneCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().getAllMissionSuccessUserInLatestMission(),
                "No one finished yet"
        );
    }
}
