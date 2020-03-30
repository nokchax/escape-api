package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
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
    protected boolean sudo;

    public Command(Message message) {
        this.message = message;
    }

    public String process() {
        if(isNotSudoer()) {
            return "Permission denied";
        }

        return internalProcess();
    }

    public abstract String internalProcess();

    protected C getProcessor() {
        return (C) processors.get(clazz);
    }

    private boolean isNotSudoer() {
        return sudo && checkAdmin();
    }

    private boolean checkAdmin() {
        AppProperties properties = (AppProperties) processors.get(AppProperties.class);

        return properties.getAdmin()
                .getIds()
                .contains(getCommander());
    }

    private String getCommander() {

        return message.getFrom()
                .getId()
                .toString();
    }

    private void extractOptions(String commandString) {
        String[] commandTokens = commandString.split("-");

        for(String commandToken : commandTokens) {
            String[] command = commandToken.split(" ");
        }
    }
}
