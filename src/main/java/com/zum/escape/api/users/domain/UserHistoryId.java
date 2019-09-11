package com.zum.escape.api.users.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryId implements Serializable {
    private String user;
    private LocalDateTime dateTime;
}
