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
    protected Map<Class<?>, Object> processors;
    protected String defaultArgumentAlias;
    protected Class<C> clazz;
    protected boolean sudo;

    public Command(Message message, Map<Class<?>, Object> processors) {
        extractOptions(message.getText());
        this.message = message;
        this.processors = processors;
    }

    public String process() {
        checkSudoer();

        return internalProcess();
    }

    public abstract String internalProcess() ;

    protected C processor() {
        return (C) processors.get(clazz);
    }

    private void checkSudoer() {
        if(sudo && !isAdmin()) {
            throw new UnAuthorizedException();
        }
    }

    private boolean isAdmin() {
        AppProperties properties = (AppProperties) processors.get(AppProperties.class);

        return properties.getAdmin()
                .getIds()
                .contains(commander());
    }

    protected String commander() {

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
