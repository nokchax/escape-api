package com.zum.escape.api.messageTest;

import com.zum.escape.api.users.dto.ProblemHistoryDto;
import org.junit.Test;

//@Slf4j
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

/*
        log.info("+----------+-------------+");
        log.info("| USERNAME | T  H   M  E |");
        log.info("+----------+-------------+");
        log.info(problemHistoryDto.toMessage());
        log.info(problemHistoryDto.toMessage());
        log.info(problemHistoryDto.toMessage());
        log.info(problemHistoryDto.toMessage());
        log.info(problemHistoryDto.toMessage());
        log.info("+----------+-------------+");
*/
    }
}
