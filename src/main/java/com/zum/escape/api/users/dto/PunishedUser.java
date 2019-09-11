package com.zum.escape.api.users.dto;

import com.zum.escape.api.users.domain.Description;
import com.zum.escape.api.users.domain.Point;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class PunishedUser {
    private User user;
    private int lackPoint;

    public UserHistory imposed(LocalDateTime imposedTime) {
        return user.getPoints(new Point(Description.FINES, -lackPoint, imposedTime));
    }
}
