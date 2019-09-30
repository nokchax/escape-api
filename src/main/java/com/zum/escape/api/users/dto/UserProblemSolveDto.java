package com.zum.escape.api.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProblemSolveDto extends Message {
    private static String liner = "------------";
    private String leetcodeId;

    @Override
    public String makeHeader() {
        StringBuilder sb = new StringBuilder();

        return sb.append("```")
                .append('\n')
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
        return String.format("%12s", leetcodeId);
    }
}
