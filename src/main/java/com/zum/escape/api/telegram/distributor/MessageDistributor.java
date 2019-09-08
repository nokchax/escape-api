package com.zum.escape.api.telegram.distributor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageDistributor {
    public String distributeMessage(String message) {
        if(message.startsWith("/"))
            message = message.substring(1);

        switch(message) {
            case "help":
                return "1.유저 등록 : /register id" +
                        "2.과제 완료 : /done" +
                        "3.과제 미완 : /todo";
            case "register":
                break;
            case "todo":
                break;
            case "done":
                break;
            default:
                log.info("Unknown command : {}", message);
        }
        //user 관련 등록, 삭제(위험)
        //weekly homework
        /**
         * 회원 등록
         *
         * 주간 과제(task)
         *
         */
        return "Unknown command : " + message;
    }
}
