package com.zum.escape.api.admin.service;

import com.zum.escape.api.observing.ObservingService;
import com.zum.escape.api.problem.service.ProblemService;
import com.zum.escape.api.scheduler.ProblemHistoryScheduler;
import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.service.UserHistoryService;
import com.zum.escape.api.users.service.UsersService;
import com.zum.escape.api.util.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final TaskService taskService;
    private final UsersService usersService;
    private final ProblemService problemService;
    private final ObservingService observingService;
    private final UserHistoryService userHistoryService;
    private final ProblemHistoryScheduler problemHistoryScheduler;

    @Value("${observer.admins}")
    private List<Integer> ADMINS;

    public String byPassMessage(Message message) {
        if(!isAdmin(message)) {
            return "Permission Denied";
        }

        // TODO: 2020-01-23 use extends
        Command command = new Command(message, true);

        switch (command.getCommand()) {
            // /register id pw name -> register user
            case "help":
                return "1. 사용자 등록 : /register id pw name\n" +
                        "2. 특정 사용자에게 포인트 주기 : /givePoint id point\n" +
                        "3. 모든 사용자에게 포인트 주기 : /givePointToAll point\n" +
                        "4. 테스크 생성 : /newTask\n" +
                        "5. 현 테스크 업데이트 : /updateTask\n" +
                        "6. 문제 리스트 업데이트 : /updateProblem\n\n";

            case "register":
                if(command.isArgsLessThan(4)) {
                    return "use like this -> /register id pw name";
                }

                return usersService.addUser(command.getArguments());

            // /su give-point id point
            case "givePoint":
                return userHistoryService.givePointToOne(
                        command.getFirstArg(),
                        Integer.parseInt(command.getSecondArg())
                ).toString();

            case "givePointToAll":
                userHistoryService.givePointToEveryUser(Integer.parseInt(command.getFirstArg()));
                return "Give : " + command.getFirstArg() + "points to all user";

            case "correct":
                problemHistoryScheduler.correctUpdate();
                return "Update complete";

            // /su newtast
            case "newtask":
                taskService.createTasks();
                return "new task created";

                // updateTask
            case "updateTask":
                taskService.updateTask();
                return "task participants updated";

            // /update-problem -> update problems
            case "updateProblem":
                problemService.saveOrUpdateProblems();
                return "problem lists updated";

            case "loginUpdate":
                problemService.saveOrUpdateProblemsLoginBase();
                return "problem lists updated(login base)";

            case "manualUpdate" :
                return taskService.updateManually(command.getFirstArg(), Long.parseLong(command.getSecondArg()));

            case "noticeHere":
                observingService.updateNoticeTargetRoom(message.getChatId());
                return "send message to this room (no. " + message.getChatId() +")";

            default:
                log.info(command.toString());
                return "Unknown command : " + command.toString();
        }
    }


    private boolean isAdmin(Message message) {
        Integer userId = message.getFrom().getId();

        log.info("Admins : {}\nuserId : {}", ADMINS.toString(), userId);

        return ADMINS.contains(userId);
    }
}
