package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.mission.service.MissionService;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

@CommandMapping(commands = {"newMission", "nm"})
public class NewMissionCommand extends Command<MissionService> implements SudoCommand {
    public NewMissionCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext);
    }

    @Override
    protected String internalProcess() {
        processor().createMission();

        return "create complete";
    }
}
