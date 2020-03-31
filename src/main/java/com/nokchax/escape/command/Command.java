package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.UnAuthorizedException;
import com.nokchax.escape.util.CommandExtractor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Data
public abstract class Command<C> {
    protected Message message;
    protected Map<String, String> options;
    protected Map<Class, Object> processors;
    protected String defaultArgumentAlias;
    protected Class<C> clazz;
    protected boolean sudo;

    public Command(Message message) {
        extractOptions(message.getText());
        this.message = message;
    }

    public String process() throws Exception {
        if(isNotSudoer()) {
            throw new UnAuthorizedException("Permission denied");
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

    protected String getCommander() {

        return message.getFrom()
                .getId()
                .toString();
    }

    private void extractOptions(String commandString) {
        this.options = CommandExtractor.extractOptions(commandString, defaultArgumentAlias);
    }

    protected String getDefaultArgument() {
        return options.getOrDefault(defaultArgumentAlias, "");
    }
}
