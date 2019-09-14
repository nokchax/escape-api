package com.zum.escape.api.admin;

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

    @Value("${observer.admins}")
    private List<Integer> ADMIN_LIST;

    public String byPassMessage(Message message) {
        if(!isAdmin(message))
            return "Permission Denied";

        Command command = new Command(message, true);

        switch (command.getCommand()) {
            // /register email pw name leetcodeName -> register user
            case "register":
                if(command.getTotalLength() < 5)
                    return "/register email pw name leetcodeName";
                return usersService.addUser(command.getArguments());

            // /su give-point id point
            case "give-point" :
                return userHistoryService.givePointToOne(
                        command.getFirstArg(),
                        Integer.parseInt(command.getSecondArg())
                ).toString();

            // /su newtast
            case "newtask":
                taskService.createTasks();
                return "new task created";
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
