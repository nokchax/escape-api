package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class ListCommand extends Command<MissionService>{

    public ListCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
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
