package com.nokchax.escape.command;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Command<C> {
    protected Message message;
    protected Map<String, String> options = new HashMap<>();
    protected String defaultArgument;
    protected String defaultArgumentAlias;
    protected Map<Class, Object> processors;
    protected Class<C> clazz;

    public Command(Message message) {
        this.message = message;
    }

    public abstract String process();

    protected C getProcessor() {
        return (C) processors.get(clazz);
    }

    private void extractOptions(String commandString) {
        String[] commandTokens = commandString.split("-");

        for(String commandToken : commandTokens) {
            String[] command = commandToken.split(" ");
        }
    }
}
