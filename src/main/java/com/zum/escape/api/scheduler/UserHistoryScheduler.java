package com.zum.escape.api.scheduler;

import com.zum.escape.api.users.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHistoryScheduler {
    private UserHistoryService userHistoryService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void givePointsToAllUser() {
        userHistoryService.givePointToEveryUser(UserHistoryService.MONTHLY_POINT);
    }

}
