package com.zum.escape.api.telegram.distributor;

import com.zum.escape.api.admin.AdminService;
import com.zum.escape.api.endpoint.problem.service.ProblemService;
import com.zum.escape.api.task.TaskService.TaskService;
import com.zum.escape.api.users.domain.UserProblemHistory;
import com.zum.escape.api.users.repository.UserProblemHistoryRepository;
import com.zum.escape.api.users.service.UsersService;
import com.zum.escape.api.util.MessageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageDistributor {
    private final UsersService usersService;
    private final TaskService taskService;
    private final ProblemService problemService;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final AdminService adminService;

    public String distributeMessage(Message message) {
        String text = message.getText();

        if(text.startsWith("/"))
            text = text.substring(1);

        String[] args = text.split(" ");

        switch(args[0]) {
            case "su":
                return adminService.byPassMessage(message);
            case "help":
                return  "1.유저 등록 : /register leetcodeId" +
                        "2.과제 완료 리스트 : /done" +
                        "3.과제 미완 리스트 : /todo";
            case "register":
                if(args.length < 5)
                    return "/register email pw name leetcodeName";
                return usersService.addUser(text);
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
                List<UserProblemHistory> all = userProblemHistoryRepository.findAll();
                StringBuilder sb = new StringBuilder();
                all.forEach( x -> {
                    sb.append(x.toString()).append("\n");
                });
                all.forEach(System.out::println);

                return sb.toString();
            default:
                log.info("Unknown command : {}", text);
        }


        return "Unknown command : " + text;
    }
}
