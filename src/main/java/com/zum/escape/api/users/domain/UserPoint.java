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
        "select " +
                "id, sum(point) as point " +
                "from user_history left join users on (users.id = user_history.user_id) " +
                "group by user_id"
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
