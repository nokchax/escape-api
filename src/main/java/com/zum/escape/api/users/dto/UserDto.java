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
    private static String liner = "+----------+---------------+";
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
    public String makeHeader() {
        StringBuilder sb = new StringBuilder();

        return sb.append("```")
                .append('\n')
                .append(liner)
                .append('\n')
                .append("| USERNAME | S   H   M   E |\n")
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
        return String.format("|%10s|%3d %3d %3d %3d|", leetcodeId.length() > 10 ? leetcodeId.replaceAll("\\d", "") : leetcodeId, score, hard, medium, easy);
    }
}
