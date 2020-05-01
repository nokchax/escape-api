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
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandMaker {
    private final ApplicationContext applicationContext;
    private Map<String, Constructor<? extends Command<?>>> constructors;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
        constructors = new HashMap<>();
        Reflections reflections = new Reflections("com.nokchax.escape.command");

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(CommandMapping.class);
        classes.forEach(clazz -> {
                    CommandMapping annotation = clazz.getAnnotation(CommandMapping.class);
                    String[] commands = annotation.commands();
                    try {
                        Constructor<? extends Command<?>> constructor = (Constructor<? extends Command<?>>) clazz.getConstructor(Message.class, ApplicationContext.class);

                        Arrays.stream(commands)
                                .forEach(command -> constructors.put(command, constructor));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }

    public Command<?> makeCommand(Message message) {
        try {
            return (Command<?>) matchCommandConstructor(message).newInstance(message, applicationContext);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Fail to change message to command");
        }
    }

    private Constructor<?> matchCommandConstructor(Message message) {
        try {
            return constructors.getOrDefault(extractText(message), UnknownCommand.class.getConstructor(Message.class, ApplicationContext.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unknown command");
        }
    }

    private String extractText(Message message) {
        String text = message.getText();
        if (StringUtils.isEmpty(text)) {
            text = message.getCaption();
        }

        if(text.startsWith("/")) {
            text = text.substring(1);
        }

        return text.split(" ")[0].trim();
    }
}
