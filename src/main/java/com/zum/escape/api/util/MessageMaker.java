package com.zum.escape.api.util;

import com.zum.escape.api.users.dto.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMaker {
    public static String dtoToMessage(List<? extends Message> dto, String defaultMessage) {
        if(dto == null || dto.isEmpty())
            return defaultMessage;

        Message message = dto.get(0);

        return message.makeHeader() +
                dto.stream()
                        .map(Message::toMessage)
                        .collect(Collectors.joining("\n")) +
                message.makeFooter();
    }
}
