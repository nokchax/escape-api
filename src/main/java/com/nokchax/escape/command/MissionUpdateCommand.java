package com.nokchax.escape.command;

import com.nokchax.escape.mission.service.MissionService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MissionUpdateCommand extends Command<MissionService> {
    public MissionUpdateCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext);
    }
    @Override
    public String internalProcess() {
        processor().updateMission();

        return "Update complete";
    }
}
