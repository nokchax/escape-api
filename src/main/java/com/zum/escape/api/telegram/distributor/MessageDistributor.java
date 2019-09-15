package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.admin.AdminService;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
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
    private final ProblemService problemService;
    private final UserProblemHistoryService userProblemHistoryService;
    private final AdminService adminService;
    private final UserProblemService userProblemService;
    private final UserPointRepository userPointRepository;

    public String distributeMessage(Message message) {
        Command command = new Command(message, false);

        switch(command.getCommand()) {
            case "su":
                return adminService.byPassMessage(message);

                //help
            case "help":
                return  "1.현황 업데이트 : /update\n" +
                        "2.과제 완료 리스트 : /done\n" +
                        "3.과제 미완 리스트 : /todo\n" +
                        "4.문제 리스트 업데이트 : /updateProblem\n" +
                        "5.포인트 조회 : /point\n" +
                        "6.벌금 조회 : /fine\n" +
                        "7.총 푼문제 내역 조회 : /history [id]\n" +
                        "8.해당 문제를 푼 사람 : /problem {problem-name}";

                // /td -> return users that dose't reached the goal
            case "todo":
                return MessageMaker.dtoToMessage(
                        taskService.getTodoList(),
                        "Everybody finished"
                );

                // /done -> return users that reached the goal
            case "done":
                return MessageMaker.dtoToMessage(
                        taskService.getDoneList(),
                        "Nobody finished yet"
                );

                // /update -> return update user's problem solve count and return every users info;
            case "update":
                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );

                // /update-problem -> update problems
            case "updateProblem":
                problemService.saveOrUpdateProblems();
                return "problem lists updated";

                // /point -> return all user's current point
            case "point":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByOrderByPointDesc(),
                        "There are no users"
                );

                // /fine -> return fine list
            case "fine":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByPointIsLessThanOrderByPointAsc(0),
                        "No fine list"
                );

                // /history or /history id -> return user problem's counting result
            case "history":
                return MessageMaker.dtoToMessage(
                        userProblemHistoryService.find(command),
                        "User not found"
                );

                // /problem {problem-name} -> return user list that solved this problem
            case "problem":
                return MessageMaker.dtoToMessage(
                       userProblemService.findAllUsersSolvedThisProblem(command),
                        "No users solved this problem"
                );

            default:
                log.info("Unknown command : {}", command.toString());
        }


        return "Unknown command : " + command.toString();
    }
}
