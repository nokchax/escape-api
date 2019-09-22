package com.zum.escape.api.util;

import com.zum.escape.api.users.dto.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMaker {
    public static String dtoToMessage(List<? extends Message> dto, String defaultMessage) {
        if(dto == null || dto.isEmpty())
            return defaultMessage;

        StringBuilder sb = new StringBuilder();
        String liner = "+----------+---------------+";
        return sb.append("```\n")
                .append(liner).append('\n')
                .append("| USERNAME | T   H   M   E |\n")
                .append(liner).append('\n')
                .append(dto.stream().map(Message::toMessage).collect(Collectors.joining("\n")))
                .append('\n').append(liner).append('\n')
                .append("```")
                .toString();
/*        return dto.stream()
                .map(Message::toMessage)
                .collect(Collectors.joining("\n"));*/
    }
}
