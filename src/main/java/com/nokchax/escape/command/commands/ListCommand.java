package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"list", "l"})
public class ListCommand extends Command<MissionService> {

    public ListCommand(Message message, ApplicationContext processors) {
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
