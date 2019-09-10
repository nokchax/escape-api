package com.zum.escape.api.util;

import com.zum.escape.api.users.dto.UserDto;

import java.util.List;

public class MessageMaker {
    public static String userDtoToMessage(List<UserDto> userDtos, String defaultMessage) {
        if(userDtos == null || userDtos.isEmpty())
            return defaultMessage;

        return String.join("\n", userDtos.toString());
    }
}
