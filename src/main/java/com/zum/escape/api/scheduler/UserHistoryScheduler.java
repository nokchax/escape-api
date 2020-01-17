package com.zum.escape.api.scheduler;

import com.zum.escape.api.users.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserHistoryScheduler {
    private final UserHistoryService userHistoryService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void givePointsToAllUser() {
        log.info("Give points to all user start");
        userHistoryService.givePointToEveryUser(UserHistoryService.MONTHLY_POINT);
        log.info("Give points to all user end");
    }

}
