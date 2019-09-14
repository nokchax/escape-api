package com.zum.escape.api.util;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
public class Command {
    private String command;
    private List<String> arguments;
    private int totalLength;

    public String getFirstArg() {
        return this.arguments.get(0);
    }
    public Command(Message message) {
        String text = message.getText();

        if(text.startsWith("/"))
            text = text.substring(1);

        String[] args = text.split(" ");

        this.totalLength = args.length;
        this.command = args[0];
        this.arguments = Arrays.asList(
                Arrays.copyOfRange(args, 1, args.length)
        );
    }
}
