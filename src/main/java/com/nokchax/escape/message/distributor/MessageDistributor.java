package com.nokchax.escape.message.distributor;

import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.point.repository.PointRepository;
import com.zum.escape.api.util.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDistributor {
    private final EntryService entryService;
    private final MissionService missionService;
    private final PointRepository pointRepository;

    public String distributeMessage(Message message) {
        Command command = new Command(message);

        switch(command.getCommand()) {
            case "su":
//                return adminService.byPassMessage(message);

            case "help":
                return "1. 문제풀고 나서 기록 업데이트 : /u or /update username\n" +
                        "2. 미션 완료 사용자 리스트 : /d or /done\n" +
                        "3. 금주 미션 point 달성 현황 리스트 : /t or /todo\n" +
                        "4. 보너스(방학) 포인트 확인 : /po or /point\n" +
                        "5. 미납 벌금 조회 : /f or /fine\n" +
                        "6. 총 푼 문제 내역조회 : /h or /history username\n" +
                        "7. 문제별 푼 사용자 리스트 : /pr or /problem 문제번호\n" +
                        "8. 금주 미션 참가자 현황 : /l or /list\n\n" +
                        "예) 문제를 푼다 > /update username > /todo";


            case "list":
            case "l":
                MessageMaker.toMessage(
                        missionService.getAllUserInLatestMission(),
                        "No users"
                );

            case "todo":
            case "t":
                MessageMaker.toMessage(
                        missionService.getAllMissioningUserInLatestMission(),
                        "Every one finished mission"
                );

            case "done":
            case "d":
                MessageMaker.toMessage(
                        missionService.getAllMissionSuccessUserInLatestMission(),
                        "No one finished yet"
                );

                // /update -> return update user's problem solve count and return every users info;
            case "update":
            case "u":
                /*
                    1. update using crawl and login crawl (bypass parameter (all, no arg, userId))
                    2. entryService.updateLatestEntry();
                    3. MessageMaker.toMessage()...
                 */
/*
                if(command.containsArgs()) {
                    return taskService.updateSpecificUser(command.getFirstArg().toLowerCase());
                }

                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );
*/

            // How to call : send file and comment like this /mu userId
            case "manualUpdate":
            case "mu":
/*
                log.info("Start manual update [first args : {}]", command.getFirstArg());
                if(file == null) {
                    log.error("File not found");
                    return "File not found";
                }

                if(command.containsArgs()) {
                    return MessageMaker.dtoToMessage(
                            taskService.updateSpecificUserManually(command.getFirstArg(), file),
                            "User not found"
                    );
                }

                return MessageMaker.dtoToMessage(
                        taskService.getAllUsers(),
                        "No users"
                );
*/
            // /point -> return all user's current point
            case "point":
            case "po":
                return MessageMaker.toMessage(
                        pointRepository.findAllUserPoint(),
                        "There is no user"
                );

            // /fine -> return fine list
            case "fine":
            case "f":
                return MessageMaker.toMessage(
                        pointRepository.findAllPenaltyUsers(),
                        "There is no penalty user"
                );

            // /history or /history id -> return user problem's counting result
            case "history":
            case "h":
/*
                return MessageMaker.dtoToMessage(
                        userProblemHistoryService.find(command),
                        "User not found"
                );
*/

            // /problem {problem-name} -> return user list that solved this problem
            case "problem":
            case "pr":
/*
                return userProblemService.findAllUsersSolvedThisProblem(command)
                        .toMessage();
*/

            case "currentStatus":
            case "cs":
//                return observingService.getCurrentStatus();

            default:
                log.info("Unknown command : {}", command.toString());
        }

        return "Unknown command : " + command.toString();
    }
}
