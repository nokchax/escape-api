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
    private String leetcodeId;

    @Override
    public String makeHeader() {
        return null;
    }

    @Override
    public String makeFooter() {
        return null;
    }

    @Override
    public String toMessage() {
        return leetcodeId;
    }
}
