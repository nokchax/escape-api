package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.repository.UserProblemHistoryRepository;
import com.zum.escape.api.users.service.UsersService;
import com.zum.escape.api.util.MessageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageDistributor {
    private final UsersService usersService;
    private final TaskService taskService;
    private final ProblemService problemService;
    private final UserProblemHistoryRepository userProblemHistoryRepository;

    public String distributeMessage(String message) {
        if(message.startsWith("/"))
            message = message.substring(1);

        String[] args = message.split(" ");

        switch(args[0]) {
            case "help":
                return  "1.유저 등록 : /register leetcodeId" +
                        "2.과제 완료 리스트 : /done" +
                        "3.과제 미완 리스트 : /todo";
            case "register":
                if(args.length < 5)
                    return "/register email pw name leetcodeName";
                System.out.println(message);
                return usersService.addUser(message);
            case "newtask":
                taskService.createTasks();
                return "new task created";
            case "todo":
                return MessageMaker.userDtoToMessage(taskService.getTodoList(), "Everybody finished");
            case "done":
                return MessageMaker.userDtoToMessage(taskService.getDoneList(), "Nobody finished yet");
            case "update":
                return taskService.update();
            case "update problem":
                problemService.saveOrUpdateProblems();
                return "problem lists updated";
            case "test":
                return userProblemHistoryRepository.findAll().toString();
            default:
                log.info("Unknown command : {}", message);
        }


        return "Unknown command : " + message;
    }
}
