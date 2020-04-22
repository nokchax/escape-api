package com.nokchax.escape.message.template;

import org.springframework.util.CollectionUtils;

import java.util.List;

public class MessageMaker {
    public static String toMessage(List<? extends MessageTemplate> dtos, String defaultMessage) {
        if(CollectionUtils.isEmpty(dtos)) {
            return defaultMessage;
        }

        return wrapMessage(buildHeader(dtos) + buildBody(dtos));
    }

    public static String toSplitMessage(List<? extends MessageTemplate> dtos, String defaultMessage) {
        if(CollectionUtils.isEmpty(dtos)) {
            return defaultMessage;
        }

        return buildHeader(dtos) + wrapMessage(buildBody(dtos));
    }

    private static String wrapMessage(String message) {
        return "```\n" + message + "```\n";
    }

    private static String buildHeader(List<? extends MessageTemplate> dtos) {
        MessageTemplate template = messageTemplate(dtos);

        return  template.header();
    }

    private static String buildBody(List<? extends MessageTemplate> dtos) {
        MessageTemplate template = messageTemplate(dtos);

        StringBuilder stringBuilder = new StringBuilder();
        dtos.stream()
                .map(MessageTemplate::body)
                .forEachOrdered(stringBuilder::append);
        stringBuilder.append(template.footer());

        return stringBuilder.toString();
    }

    private static MessageTemplate messageTemplate(List<? extends MessageTemplate> dtos) {

        return dtos.get(0);
    }
}
