package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.command.SudoCommand;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.point.service.PointService;
import lombok.Data;
import org.jsoup.helper.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

@CommandMapping(commands = {"givePoint", "givepoint", "gp"})
public class GivePointCommand extends Command<PointService> implements SudoCommand {
    private static final String USER_ID = "u";
    private static final String POINT = "p";

    public GivePointCommand(Message message, ApplicationContext processors) {
        super(message, processors, Arrays.asList(USER_ID, POINT));
    }

    @Override
    public String internalProcess() {
        return MessageMaker.toMessage(
                processor().givePointTo(new PointArgument(getTargetUser(), getPoint())),
                "Fail to give point, maybe user not exist"
        );
    }

    private String getTargetUser() {
        return getArgument(USER_ID);
    }

    private String getPoint() {
        return getArgument(POINT);
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
            if (StringUtils.isEmpty(targetUser) || StringUtils.isEmpty(point) || !StringUtil.isNumeric(point)) {
                throw new IllegalArgumentException(
                        "parameter is not correct, command like below template\n" +
                                "/givePoint -u {id | all} -p {point}"
                );
            }
        }
    }
}
