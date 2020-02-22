package com.nokchax.escape.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserPointHistoryId implements Serializable {
    private String user;
    private LocalDateTime dateTime;
}
