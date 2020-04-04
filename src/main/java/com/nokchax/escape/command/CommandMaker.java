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
        switch (extractText(message)) {
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

            case "history":
            case "h":
                return new HistoryCommand(message);

            case "problem":
            case "p":
                return new ProblemCommand(message);

            case "updateProblem":
            case "up":
                return new UpdateProblemCommand(message);

            case "register":
                return new RegisterCommand(message);

            case "givePoint":
            case "gp":
                return new GivePointCommand(message);

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
