package com.nokchax.escape.message.distributor;

import com.nokchax.escape.command.CommandMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDistributor {
    private final CommandMaker commandMaker;

    public String distributeMessage(Message message) {
        try {
            return commandMaker.makeCommand(message)
                    .process();

        } catch (Exception e) {
            log.error("{}", e.getMessage());
            e.printStackTrace();

            return e.getMessage();
        }
    }
}
