package com.zum.escape.api.users.dto;

import com.zum.escape.api.users.domain.Description;
import com.zum.escape.api.users.domain.Point;
import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PunishedUser extends Message {
    private static String liner = "--------------+-------";
    private User user;
    private int lackPoint;

    public UserHistory imposed(LocalDateTime imposedTime) {
        return user.getPoints(new Point(Description.FINES, -lackPoint, imposedTime));
    }

    @Override
    public String makeHeader() {
        StringBuilder sb = new StringBuilder();

        return sb.append("```")
                .append('\n')
                .append(liner)
                .append('\n')
                .append("   USERNAME   | POINT \n")
                .append(liner)
                .append('\n')
                .toString();
    }

    @Override
    public String makeFooter() {
        return "\n" + liner + "\n```";
    }

    @Override
    public String toMessage() {
        return String.format(" %12s | %5d ", user.getId(), lackPoint);
    }
}
