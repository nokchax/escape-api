package com.nokchax.escape.message.template;

import org.springframework.util.CollectionUtils;

import java.util.List;

public class MessageMaker {
    public static String toMessage(List<? extends MessageTemplate> dtos, String defaultMessage) {
        if(CollectionUtils.isEmpty(dtos)) {
            return defaultMessage;
        }

        return buildMessage(dtos);
    }


    private static String buildMessage(List<? extends MessageTemplate> dtos) {
        MessageTemplate template = messageTemplate(dtos);

        StringBuilder stringBuilder = new StringBuilder("```\n" + template.header());
        dtos.forEach(stringBuilder::append);
        stringBuilder.append(template.footer())
                .append("```\n");

        return stringBuilder.toString();
    }

    private static MessageTemplate messageTemplate(List<? extends MessageTemplate> dtos) {

        return dtos.get(0);
    }
}
