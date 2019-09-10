package com.zum.escape.api.users.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProblemId implements Serializable {
    private String user;
    private Long problem;
}
