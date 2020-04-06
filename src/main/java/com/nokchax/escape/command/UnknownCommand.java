package com.nokchax.escape.command;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class UnknownCommand extends Command<Object> {
    public UnknownCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return "Unknown command";
    }
}
