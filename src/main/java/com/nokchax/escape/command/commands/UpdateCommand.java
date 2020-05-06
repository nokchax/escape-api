package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

@CommandMapping(commands = {"update", "u"})
public class UpdateCommand extends Command<UpdateService> {
    private static final String USER_ID = "u";
    private static final String ALL = "all";

    public UpdateCommand(Message message, ApplicationContext processors) {
        super(message, processors, Collections.singletonList(USER_ID));
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().updateLatestMissionAndReturnEntry(extractArgument()),
                "User not found"
        );
    }

    private UpdateArgument extractArgument() {
        return UpdateArgument.builder()
                .target(getDefaultArgument())
                .requestUsersTelegramId(commander())
                .build();
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateArgument {
        public static final UpdateArgument UPDATE_ALL = new UpdateArgument(ALL, "");
        private final String target;
        private final String requestUsersTelegramId;

        public boolean isEmptyArgument() {
            return StringUtils.isEmpty(target);
        }
    }
}
