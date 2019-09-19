package com.zum.escape.api.messageTest;

import com.zum.escape.api.users.dto.ProblemHistoryDto;
import org.junit.Test;

public class MessageTest {

    @Test
    public void testToMessage() {
        ProblemHistoryDto problemHistoryDto = ProblemHistoryDto.builder()
                .userId("nokchax")
                .totalCount(999)
                .hardCount(900)
                .mediumCount(90)
                .easyCount(9)
                .build();

        System.out.println("--------------------------------------");
        System.out.println("|     ID     |  T  |  H  |  M  |  E  |");
        System.out.println("--------------------------------------");
        System.out.println(problemHistoryDto.toMessage());
    }
}
