package com.nokchax.escape.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CommandMaker {
    private final ApplicationContext applicationContext;

    public Command<?> makeCommand(Message message) {
        switch (extractText(message)) {
            case "list":
            case "l":
                return new ListCommand(message, applicationContext);

            case "todo":
            case "t":
                return new TodoCommand(message, applicationContext);

            case "done":
            case "d":
                return new DoneCommand(message, applicationContext);

            case "help":
                return new HelpCommand(message, applicationContext);

            case "point":
            case "po":
                return new PointCommand(message, applicationContext);

            case "fine":
            case "f":
                return new FineCommand(message, applicationContext);

            case "history":
            case "h":
                return new HistoryCommand(message, applicationContext);

            case "problem":
            case "p":
                return new ProblemCommand(message, applicationContext);

            case "update" :
            case "u":
                return new UpdateCommand(message, applicationContext);

            case "updateProblem":
            case "updateproblem":
            case "up":
                return new UpdateProblemCommand(message, applicationContext);

            case "register":
                return new RegisterCommand(message, applicationContext);

            case "givePoint":
            case "givepoint":
            case "gp":
                return new GivePointCommand(message, applicationContext);

            default:
                return new UnknownCommand(message, applicationContext);
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
