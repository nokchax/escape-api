package com.zum.escape.api.users.domain;

import com.zum.escape.api.users.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Immutable
@Subselect(
        "SELECT " +
                "ID, SUM(POINT) AS POINT " +
                "FROM USER_HISTORY LEFT JOIN USERS ON (USERS.ID = USER_HISTORY.USER_ID) " +
                "GROUP BY USER_ID"
)
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint extends Message {
    @Id
    private String id;
    private int point;

    @Override
    public String toMessage() {
        return id + ": " + point;
    }
}
