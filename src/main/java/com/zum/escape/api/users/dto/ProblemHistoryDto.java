package com.zum.escape.api.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemHistoryDto extends Message {
    private static String liner = "----------+---------------";
    private String userId;
    private int totalCount;
    private int hardCount;
    private int mediumCount;
    private int easyCount;

    private String getShortenName() {
        return userId.length() > 10 ? userId.replaceAll("\\d", "") : userId;
    }

    @Override
    public String makeHeader() {
        StringBuilder sb = new StringBuilder();

        return sb.append("```")
                .append('\n')
                .append(liner)
                .append('\n')
                .append(" USERNAME | T   H   M   E \n")
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
        return String.format("%10s|%3d %3d %3d %3d", this.getShortenName(), totalCount, hardCount, mediumCount, easyCount);
    }
}
