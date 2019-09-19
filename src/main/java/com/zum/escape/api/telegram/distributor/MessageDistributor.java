package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.admin.AdminService;
import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.repository.UserPointRepository;
import com.zum.escape.api.users.service.UserProblemHistoryService;
import com.zum.escape.api.users.service.UserProblemService;
import com.zum.escape.api.util.Command;
import com.zum.escape.api.util.MessageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDistributor {
    private final TaskService taskService;
    private final UserProblemHistoryService userProblemHistoryService;
    private final AdminService adminService;
    private final UserProblemService userProblemService;
    private final UserPointRepository userPointRepository;

    public String distributeMessage(Message message) {
        Command command = new Command(message);

        switch(command.getCommand()) {
            case "su":
                return adminService.byPassMessage(message);

                //help
            case "help":
                return "1. 문제풀고 나서 기록 업데이트 : /u or /update username\n" +
                        "2. 미션 완료 사용자 리스트 : /d or /done\n" +
                        "3. 금주 미션 point 달성 현황 리스트 : /t or /todo\n" +
                        "4. 보너스(방학) 포인트 확인 : /po or /point\n" +
                        "5. 미납 벌금 조회 : /f or /fine\n" +
                        "6. 총 푼 문제 내역조회 : /h or /history username\n" +
                        "7. 문제별 푼 사용자 리스트 : /pr or /problem 문제번호\n\n" +
                        "예) 문제를 푼다 > /update username > /todo";

                // /td -> return users that dose't reached the goal
            case "todo":
            case "t":
                return MessageMaker.dtoToMessage(
                        taskService.getTodoList(),
                        "Everybody finished"
                );

                // /done -> return users that reached the goal
            case "done":
            case "d":
                return MessageMaker.dtoToMessage(
                        taskService.getDoneList(),
                        "Nobody finished yet"
                );

                // /update -> return update user's problem solve count and return every users info;
            case "update":
            case "u":
                if(command.containsArgs()) {
                    return taskService.updateSpecificUser(command.getFirstArg());
                }

                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );
                // /point -> return all user's current point
            case "point":
            case "po":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByOrderByPointDesc(),
                        "There are no users"
                );

                // /fine -> return fine list
            case "fine":
            case "f":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByPointIsLessThanOrderByPointAsc(0),
                        "No fine list"
                );

                // /history or /history id -> return user problem's counting result
            case "history":
            case "h":
                return MessageMaker.dtoToMessage(
                        userProblemHistoryService.find(command),
                        "User not found"
                );

                // /problem {problem-name} -> return user list that solved this problem
            case "problem":
            case "pr":
                return userProblemService.findAllUsersSolvedThisProblem(command)
                        .toMessage();

            default:
                log.info("Unknown command : {}", command.toString());
        }


        return "Unknown command : " + command.toString();
    }
}
