package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.PermissionDeniedException;
import com.nokchax.escape.util.CommandExtractor;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.ParameterizedType;
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

    @SuppressWarnings("unchecked")
    public Command(Message message, ApplicationContext applicationContext, List<String> requiredOptions) {
        this.message = message;
        this.applicationContext = applicationContext;
        this.requiredOptions = requiredOptions;
        clazz = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        extractOptions(message.getText());
    }

    public Command(Message message, ApplicationContext applicationContext) {
        this(message, applicationContext, Collections.emptyList());
    }

    public String process() {
        checkAdmin();

        return internalProcess();
    }

    public abstract String internalProcess() ;

    protected C processor() {
        return applicationContext.getBean(clazz);
    }

    private void checkAdmin() {
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

    protected void extractOptions(String commandString) {
        this.options = CommandExtractor.extractOptions(commandString, defaultArgumentAlias);
    }

    protected String getDefaultArgument() {
        return options.getOrDefault(defaultArgumentAlias, "");
    }

    protected boolean allOptionsExist() {
        Map<String, String> options = getOptions();

        return requiredOptions.stream()
                .allMatch(options::containsKey);
    }
}
