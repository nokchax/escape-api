package com.nokchax.escape.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CommandMaker {

    public Command<?> makeCommand(Message message) {

        String command = extractText(message);

        switch (command) {
            case "list":
            case "l":
                return new ListCommand(message);

            case "todo":
            case "t":
                return new TodoCommand(message);

            case "done":
            case "d":
                return new DoneCommand(message);

            case "help":
                return new HelpCommand(message);

            case "point":
            case "po":
                return new PointCommand(message);

            case "fine":
            case "f":
                return new FineCommand(message);

            default:
                return new UnknownCommand(message);
        }
    }


    @PostConstruct
    public void init() {
//        Arrays.stream(this.getClass().getFields())
//                .map()
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
