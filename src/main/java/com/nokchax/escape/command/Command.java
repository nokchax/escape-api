package com.nokchax.escape.command;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.exception.PermissionDeniedException;
import com.nokchax.escape.util.CommandExtractor;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public abstract class Command<C> {
    protected final Message message;
    protected final ApplicationContext applicationContext;
    protected final List<String> requiredOptions;
    protected Map<String, String> options;
    protected String defaultArgumentAlias;
    protected Class<C> clazz;
    protected boolean sudo;

    //order is important
    public Command(Message message, ApplicationContext applicationContext, List<String> requiredOptions) {
        this.message = message;
        this.applicationContext = applicationContext;
        this.requiredOptions = requiredOptions;
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

    protected void initOptions() {
        this.options = CommandExtractor.extractOptions(this.message.getText(), defaultArgumentAlias);
    }

    protected String getDefaultArgument() {
        return options.getOrDefault(defaultArgumentAlias, "");
    }

    protected boolean allOptionsExist() {
        Map<String, String> options = getOptions();

        return requiredOptions.stream()
                .allMatch(options::containsKey);
    }

    private void initDefaultArgument() {
        if(!CollectionUtils.isEmpty(requiredOptions)) {
            this.defaultArgumentAlias = requiredOptions.get(0);
        }
    }

    private void initSudo() {
        if(this instanceof SudoCommand) {
            this.sudo = true;
        }
    }

    @SuppressWarnings("unchecked")
    private void initClass() {
        clazz = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
