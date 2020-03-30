package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TodoCommand extends Command<MissionService> {

    public TodoCommand(Message message) {
        super(message);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                getProcessor().getAllMissioningUserInLatestMission(),
                "Every one finished mission"
        );
    }
}
