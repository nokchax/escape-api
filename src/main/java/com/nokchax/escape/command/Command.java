package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.PermissionDeniedException;
import com.nokchax.escape.util.CommandExtractor;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Command<C> {
    private final Message message;
    private final ApplicationContext applicationContext;
    private final List<String> options;
    private Map<String, String> inputOptions;
    private String defaultArgumentAlias;
    private Class<C> clazz;
    private boolean sudo;

    //order is important
    public Command(Message message, ApplicationContext applicationContext, List<String> options) {
        this.message = message;
        this.applicationContext = applicationContext;
        this.options = options;
        initClass();
        initOptions();
        initDefaultArgument();
        initSudo();
    }

    public Command(Message message, ApplicationContext applicationContext) {
        this(message, applicationContext, Collections.emptyList());
    }

    public String process() {
        checkAdmin();

        return internalProcess();
    }

    protected abstract String internalProcess() ;

    protected C processor() {
        return applicationContext.getBean(clazz);
    }

    protected String commander() {

        return message.getFrom()
                .getId()
                .toString();
    }

    protected String getDefaultArgument() {
        return getArgument(defaultArgumentAlias);
    }

    protected String getArgument(String argumentName) {
        return inputOptions.getOrDefault(argumentName, "");
    }

    protected boolean allOptionsExist() {
        return options.stream()
                .allMatch(inputOptions::containsKey);
    }

    private void initDefaultArgument() {
        if(!CollectionUtils.isEmpty(options)) {
            this.defaultArgumentAlias = options.get(0);
        }
    }

    private void initSudo() {
        this.sudo = this instanceof SudoCommand;
    }

    @SuppressWarnings("unchecked")
    private void initClass() {
        this.clazz = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    private void initOptions() {
        this.inputOptions = CommandExtractor.extractOptions(this.message.getText(), defaultArgumentAlias);
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

}
