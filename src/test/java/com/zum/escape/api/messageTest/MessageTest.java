package com.zum.escape.api.messageTest;

import com.zum.escape.api.users.dto.ProblemHistoryDto;
import org.junit.Test;

public class MessageTest {

    @Test
    public void testToMessage() {
        ProblemHistoryDto problemHistoryDto = ProblemHistoryDto.builder()
                .userId("nok1231chax9123")
                .totalCount(999)
                .hardCount(900)
                .mediumCount(90)
                .easyCount(9)
                .build();

        System.out.println("+----------+-------------+");
        System.out.println("| USERNAME | T  H   M  E |");
        System.out.println("+----------+-------------+");
        System.out.println(problemHistoryDto.toMessage());
        System.out.println(problemHistoryDto.toMessage());
        System.out.println(problemHistoryDto.toMessage());
        System.out.println(problemHistoryDto.toMessage());
        System.out.println(problemHistoryDto.toMessage());
        System.out.println("+----------+-------------+");
    }
}
