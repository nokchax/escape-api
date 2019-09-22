package com.zum.escape.api.users.domain;

import com.zum.escape.api.users.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@Immutable
@Subselect(
        "select " +
                "id, sum(point) as point " +
                "from user_history left join users on (users.id = user_history.user_id) " +
                "group by user_id"
)
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint extends Message {
    @Transient
    private static String liner = "+--------------+-------+";
    @Id
    private String id;
    private int point;

    @Override
    public String makeHeader() {
        StringBuilder sb = new StringBuilder();

        return sb.append("```")
                .append('\n')
                .append(liner)
                .append('\n')
                .append("|   USERNAME   | POINT |\n")
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
        return String.format("| %12s | %5d |", id, point);
    }
}
