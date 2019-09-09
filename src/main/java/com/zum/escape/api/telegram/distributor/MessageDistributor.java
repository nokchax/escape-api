package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageDistributor {
    private final UsersService usersService;

    public String distributeMessage(String message) {
        if(message.startsWith("/"))
            message = message.substring(1);

        String[] args = message.split(" ");

        switch(args[0]) {
            case "help":
                return "1.유저 등록 : /register id" +
                        "2.과제 완료 : /done" +
                        "3.과제 미완 : /todo";
            case "register":
                if(args.length < 2)
                    return "/register id";
                return usersService.addUser(args[1]);
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
