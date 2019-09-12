package com.zum.escape.api.users.domain;

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
                "LEETCODE_NAME AS ID, SUM(POINT) AS POINT " +
                "FROM USER_HISTORY LEFT JOIN USERS ON (USERS.EMAIL = USER_HISTORY.USER_ID) " +
                "GROUP BY USER_ID"
)
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint {
    @Id
    private String id;
    private int point;

    @Override
    public String toString() {
        return id + ": " + point;
    }
}
