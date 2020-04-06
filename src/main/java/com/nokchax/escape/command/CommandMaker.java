package com.nokchax.escape.command;

import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.point.repository.PointRepository;
import com.nokchax.escape.point.service.PointService;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.repository.ProblemSolveHistoryRepository;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandMaker {
    private final UserService userService;
    private final PointService pointService;
    private final UpdateService updateService;
    private final MissionService missionService;
    private final PointRepository pointRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSolveHistoryRepository problemSolveHistoryRepository;

    private Map<Class<?>, Object> processors;

    private Map<Class<?>, Object> getProcessors() {
        if(processors == null) {
            initProcessorMap();
        }

        return processors;
    }

    @PostConstruct
    public void initProcessorMap() {
        this.processors = new HashMap<>();

        for(Field field : this.getClass().getFields()) {
            try {
                processors.put(field.getType(), field.get(field.getName()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Command<?> makeCommand(Message message) {
        switch (extractText(message)) {
            case "list":
            case "l":
                return new ListCommand(message, getProcessors());

            case "todo":
            case "t":
                return new TodoCommand(message, getProcessors());

            case "done":
            case "d":
                return new DoneCommand(message, getProcessors());

            case "help":
                return new HelpCommand(message, getProcessors());

            case "point":
            case "po":
                return new PointCommand(message, getProcessors());

            case "fine":
            case "f":
                return new FineCommand(message, getProcessors());

            case "history":
            case "h":
                return new HistoryCommand(message, getProcessors());

            case "problem":
            case "p":
                return new ProblemCommand(message, getProcessors());

            case "update" :
            case "u":
                return new UpdateCommand(message, getProcessors());

            case "updateProblem":
            case "up":
                return new UpdateProblemCommand(message, getProcessors());

            case "register":
                return new RegisterCommand(message, getProcessors());

            case "givePoint":
            case "gp":
                return new GivePointCommand(message, getProcessors());

            default:
                return new UnknownCommand(message, getProcessors());
        }
    }

    private String extractText(Message message) {
        String text = message.getText();
        if (StringUtils.isEmpty(text)) {
            text = message.getCaption();
        }

        if(text.startsWith("/")) {
            text = text.substring(1);
        }

        return text.split(" ")[0].trim();
    }
}
