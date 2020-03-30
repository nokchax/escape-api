package com.nokchax.escape.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public class UnknownCommand extends Command<Object> {
    public UnknownCommand(Message message) {
        super(message);
    }

    @Override
    public String process() {
        return "Unknown command";
    }
}
