package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DoneCommand extends Command<MissionService> {
    public DoneCommand(Message message) {
        super(message);
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().getAllMissionSuccessUserInLatestMission(),
                "No one finished yet"
        );
    }
}
