package com.zum.escape.api.users.dto;

import com.zum.escape.api.problem.domain.entity.Problem;
import com.zum.escape.api.util.MessageMaker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemDto {
    private Problem problem;
    private List<UserProblemSolveDto> userProblemSolveDto = new ArrayList<>();

    public String toMessage() {
        return problem.getDifficulty() + "\n" +
                "[" + problem.getTitle() + "] : " + userProblemSolveDto.size() +"명\n"
                + problem.leetcodeUrl() + "\n"
                + MessageMaker.dtoToMessage(
                this.userProblemSolveDto,
                "No users solved this problem"
        );
    }
}
