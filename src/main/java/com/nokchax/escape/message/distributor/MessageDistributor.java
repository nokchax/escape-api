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

                // /update -> return update user's problem solve count and return every users info;
/*
            case "update":
            case "u":
*/
                /*
                    1. update using crawl and login crawl (bypass parameter (all, no arg, userId))
                    2. entryService.updateLatestEntry();
                    3. MessageMaker.toMessage()...
                 */
/*
                if(command.containsArgs()) {
                    return taskService.updateSpecificUser(command.getFirstArg().toLowerCase());
                }

                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );
*/

            // How to call : send file and comment like this /mu userId
/*
            case "manualUpdate":
            case "mu":
*/
/*
                log.info("Start manual update [first args : {}]", command.getFirstArg());
                if(file == null) {
                    log.error("File not found");
                    return "File not found";
                }

                if(command.containsArgs()) {
                    return MessageMaker.dtoToMessage(
                            taskService.updateSpecificUserManually(command.getFirstArg(), file),
                            "User not found"
                    );
                }

                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );
*/
    }
}
