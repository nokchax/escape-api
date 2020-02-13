package com.nokchax.escape.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPointHistoryId {
    private String user;
    private LocalDateTime dateTime;
}
