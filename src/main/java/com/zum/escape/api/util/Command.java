package com.zum.escape.api.util;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Command {
    private String command;
    private List<String> arguments;

    public String getFirstArg() {
        return this.arguments.get(0);
    }

    public String getSecondArg() {
        return this.arguments.get(1);
    }

    public Command(Message message) {
        this(message, false);
    }

    public Command(Message message, boolean isSudo) {
        String text = message.getText();
        if(isSudo)
            text = text.replaceFirst("su ", "");

        if(text.startsWith("/"))
            text = text.substring(1);

        String[] args = text.split(" ");

        this.command = args[0];

        this.arguments = args.length < 2 ? new ArrayList<>() : Arrays.asList(
                Arrays.copyOfRange(args, 1, args.length)
        );
    }

    public boolean isArgsEmpty() {
        return this.arguments.isEmpty();
    }

    public boolean containsArgs() {
        return !this.isArgsEmpty();
    }

    @Override
    public String toString() {
        return command;
    }
}
