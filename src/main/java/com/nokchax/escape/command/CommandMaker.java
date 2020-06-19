package com.nokchax.escape.command;

import com.nokchax.escape.command.commands.UnknownCommand;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandMaker {
    private final ApplicationContext applicationContext;
    private Constructor<? extends Command<?>> UNKNOWN_COMMAND_CONSTRUCTOR;
    private Map<String, Constructor<? extends Command<?>>> constructors;

    public Command<?> makeCommand(Message message) {
        try {
            return constructors.getOrDefault(extractText(message), UNKNOWN_COMMAND_CONSTRUCTOR)
                    .newInstance(message, applicationContext);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Fail to change message to command : " + e.getMessage());
        }
    }

    private String extractText(Message message) {
        String text = message.getText();
        if (StringUtils.isEmpty(text)) {
            text = message.getCaption();
        }

        if (text.startsWith("/")) {
            text = text.substring(1);
        }

        return text.split(" ")[0].trim();
    }

    // TODO: 2020-06-14 do not make new instance but invoke instance and send message
    @PostConstruct
    private void init() throws NoSuchMethodException {
        constructors = new HashMap<>();
        UNKNOWN_COMMAND_CONSTRUCTOR = UnknownCommand.class
                .getConstructor(Message.class, ApplicationContext.class);

        //load commands using reflection
        new Reflections("com.nokchax.escape.command")
                .getTypesAnnotatedWith(CommandMapping.class)
                .forEach(this::putConstructor);
    }

    private void putConstructor(Class<?> clazz) {
        CommandMapping annotation = clazz.getAnnotation(CommandMapping.class);
        Arrays.stream(annotation.commands())
                .forEach(command -> matchCommandAndConstructor(command, clazz));
    }

    @SuppressWarnings("unchecked")
    private void matchCommandAndConstructor(String command, Class<?> clazz) {
        Constructor<? extends Command<?>> constructor = null;
        try {
            constructor = (Constructor<? extends Command<?>>) clazz.getConstructor(Message.class, ApplicationContext.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (constructors.containsKey(command)) {
            throw new RuntimeException("Command key duplicated");
        }

        constructors.put(command, constructor);
    }
    // TODO: 2020-05-14 make help statement using annotation
}
