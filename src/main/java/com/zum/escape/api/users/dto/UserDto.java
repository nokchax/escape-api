package com.zum.escape.api.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String leetcodeId;
    private int score;

    @Override
    public String toString() {
        return leetcodeId + ": " + score;
    }
}
