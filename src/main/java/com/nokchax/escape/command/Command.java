package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.PermissionDeniedException;
import com.nokchax.escape.util.CommandExtractor;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public abstract class Command<C> {
    protected Message message;
    protected List<String> requiredOptions;
    protected Map<String, String> options;
    protected ApplicationContext applicationContext;
    protected String defaultArgumentAlias;
    protected Class<C> clazz;
    protected boolean sudo;

    public Command(Message message, ApplicationContext applicationContext) {
        extractOptions(message.getText());
        this.message = message;
        this.applicationContext = applicationContext;
        this.requiredOptions = Collections.emptyList();
    }

    public String process() {
        checkSudoer();

        return internalProcess();
    }

    public abstract String internalProcess() ;

    protected C processor() {
        return applicationContext.getBean(clazz);
    }

    private void checkSudoer() {
        if(sudo && !isAdmin()) {
            throw new PermissionDeniedException();
        }
    }

    private boolean isAdmin() {
        AppProperties properties = applicationContext.getBean(AppProperties.class);

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

    protected boolean allOptionsExist() {
        Map<String, String> options = getOptions();

        return requiredOptions.stream()
                .noneMatch(options::containsKey);
    }
}
