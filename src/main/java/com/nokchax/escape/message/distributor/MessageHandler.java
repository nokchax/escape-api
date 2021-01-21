package com.nokchax.escape.message.distributor;

import com.nokchax.escape.command.CommandMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final CommandMaker commandMaker;

    public String handle(Message message) {
        // todo: Wrap message and overwrite toString
        log.info("By[{} : {}] : [{}]", message.getFrom().getUserName(), message.getFrom().getId(), message.getText());
        try {
            return commandMaker.makeCommand(message).process();
        } catch (Exception e) {
            log.error("Fail to handle message : [{}]", e.getMessage());
            return e.getMessage();
        }
    }
}
