package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.message.template.MessageMaker;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UpdateCommand extends Command<UpdateService> {
    public UpdateCommand(Message message) {
        super(message);
        this.defaultArgumentAlias = "u";
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().updateLatestMission(extractArgument()),
                "User not found"
        );
    }

    private UpdateArgument extractArgument() {
        return UpdateArgument.builder()
                .target(getDefaultArgument())
                .requestUsersTelegramId(commander())
                .build();
    }

    @Data
    @Builder
    public static class UpdateArgument {
        public static final UpdateArgument UPDATE_ALL_ARGUMENT = new UpdateArgument("all", "");
        private String target;
        private String requestUsersTelegramId;

        public boolean isEmptyArgument() {
            return StringUtils.isEmpty(target);
        }
    }
}
