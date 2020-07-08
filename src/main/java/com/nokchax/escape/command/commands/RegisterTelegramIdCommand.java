package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.user.service.UserService;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

@CommandMapping(commands = {"telegram"})
public class RegisterTelegramIdCommand extends Command<UserService> implements SudoCommand {
    private static final String USER_ID = "u";
    private static final String TELEGRAM_ID = "t";

    public RegisterTelegramIdCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext, Arrays.asList(USER_ID, TELEGRAM_ID));
    }

    @Override
    public String internalProcess() {
        if (!allOptionsExist()) {
            return "parameter is not correct, command like below template\n" +
                    "/telegram -u {userId} -t {telegramId}";
        }

        processor().updateTelegramId(new UpdateTelegramIdArgument(getUserId(), getTelegramId()));

        return "update complete";
    }

    private String getUserId() {
        return getArgument(USER_ID);
    }

    private String getTelegramId() {
        return getArgument(TELEGRAM_ID);
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateTelegramIdArgument {
        private final String userId;
        private final String telegramId;

        public UpdateTelegramIdArgument(String userId, String telegramId) {
            this.userId = userId;
            this.telegramId = telegramId;
        }
    }
}
