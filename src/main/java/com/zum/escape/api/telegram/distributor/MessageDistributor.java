package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.admin.AdminService;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.repository.UserPointRepository;
import com.zum.escape.api.users.repository.UserProblemHistoryRepository;
import com.zum.escape.api.users.service.UsersService;
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
    private final UsersService usersService;
    private final TaskService taskService;
    private final ProblemService problemService;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final AdminService adminService;
    private final UserPointRepository userPointRepository;

    public String distributeMessage(Message message) {
        Command command = new Command(message);

        switch(command.getCommand()) {
            case "su":
                return adminService.byPassMessage(message);
            case "help":
                return  "1.유저 등록 : /register leetcodeId" +
                        "2.과제 완료 리스트 : /done" +
                        "3.과제 미완 리스트 : /todo";

            case "register":
                if(command.getTotalLength() < 5)
                    return "/register email pw name leetcodeName";
                return usersService.addUser(command.getFirstArg());

            case "newtask":
                taskService.createTasks();
                return "new task created";

            case "todo":
                return MessageMaker.dtoToMessage(
                        taskService.getTodoList(),
                        "Everybody finished"
                );

            case "done":
                return MessageMaker.dtoToMessage(
                        taskService.getDoneList(),
                        "Nobody finished yet"
                );

            case "update":
                return taskService.update();

            case "update problem":
                problemService.saveOrUpdateProblems();
                return "problem lists updated";

            case "point":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByOrderByPointDesc(),
                        "There are no users"
                );

            case "fine":
                return MessageMaker.dtoToMessage(
                        userPointRepository.findAllByPointIsLessThanOrderByPointAsc(0),
                        "No fine list"
                );

            case "test":
                userPointRepository.findAllByOrderByPointDesc();
                userPointRepository.findAllByPointIsLessThanOrderByPointAsc(0);

                return "hi";
            default:
                log.info("Unknown command : {}", command.toString());
        }


        return "Unknown command : " + command.toString();
    }
}
