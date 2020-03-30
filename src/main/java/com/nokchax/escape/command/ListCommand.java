package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;

public class ListCommand extends Command<MissionService>{

    @Override
    public String process() {
        MissionService processor = getProcessor();

        return MessageMaker.toMessage(
                processor.getAllUserInLatestMission(),
                "No users"
        );
    }
}
