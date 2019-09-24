package com.zum.escape.api.admin.service;

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
    private final UserHistoryService userHistoryService;
    private final TaskService taskService;
    private final UsersService usersService;
    private final ProblemHistoryScheduler problemHistoryScheduler;
    private final ProblemService problemService;

    @Value("${observer.admins}")
    private List<Integer> ADMIN_LIST;

    public String byPassMessage(Message message) {
        if(!isAdmin(message))
            return "Permission Denied";

        Command command = new Command(message, true);

        switch (command.getCommand()) {
            // /register id pw name -> register user
            case "register":
                if(command.getTotalLength() < 4)
                    return "/register id pw name";
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

        }

        log.info(command.toString());
        return command.toString();
    }


    private boolean isAdmin(Message message) {
        Integer userId = message.getFrom().getId();
        log.info(ADMIN_LIST.toString());
        log.info("{}", userId);

        return ADMIN_LIST.contains(userId);
    }
}
