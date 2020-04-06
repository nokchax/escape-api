package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.service.PointService;
import lombok.Data;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class GivePointCommand extends Command<PointService> {

    public GivePointCommand(Message message, Map<Class<?>, Object> processors) {
        super(message, processors);
        sudo = true;
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().givePointTo(new PointArgument(getTargetUser(), getPoint())),
                "Fail to give point myabe user not exist"
        );
    }

    private String getTargetUser() {
        return options.getOrDefault("u", "");
    }

    private String getPoint() {
        return options.getOrDefault("p", "");
    }

    @Data
    public static class PointArgument {
        private String targetUser;
        private int point;

        public PointArgument(String targetUser, String point) {
            validateInput(targetUser, point);

            this.targetUser = targetUser;
            this.point = Integer.parseInt(point);
        }

        private void validateInput(String targetUser, String point) {
            if(StringUtils.isEmpty(targetUser) || StringUtils.isEmpty(point) || !StringUtil.isNumeric(point)) {
                throw new IllegalArgumentException(
                        "parameter is not correct, command like below form\n" +
                                "/givePoint -u {id | all} -p {point}"
                );
            }
        }
    }
}
