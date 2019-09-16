package com.zum.escape.api.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends Message {
    private String leetcodeId;
    private int score;
    private int hard;
    private int medium;
    private int easy;

    @Override
    public String toString() {
        return leetcodeId + ": " + score;
    }

    @Override
    public String toMessage() {
        return "[" + leetcodeId + "] total: " + score + " / hard: " + hard + " / medium: " + medium + " / easy: " + easy;
    }
}
