package com.nokchax.escape.command;

import com.nokchax.escape.user.service.UserService;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

public class RegisterTelegramIdCommand extends Command<UserService> {

    public RegisterTelegramIdCommand(Message message, ApplicationContext applicationContext) {
        super(message, applicationContext);
        this.sudo = true;
        this.defaultArgumentAlias = "u";
        this.requiredOptions = Arrays.asList("u", "t");
    }

    @Override
    public String internalProcess() {
        if(!allOptionsExist()) {
            return "parameter is not correct, command like below template\n" +
                    "/telegram -u {userId} -t {telegramId}";
        }

        processor().updateTelegramId(new UpdateTelegramIdArgument(getOptions().get("u"), getOptions().get("p")));

        return "update complete";
    }


    @Data
    @Builder
    public static class UpdateTelegramIdArgument {
        private String userId;
        private String telegramId;

        public UpdateTelegramIdArgument(String userId, String telegramId) {
            this.userId = userId;
            this.telegramId = telegramId;
        }
    }
}
