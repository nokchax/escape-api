package com.nokchax.escape.schedule;

import com.nokchax.escape.command.GivePointCommand;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointScheduler {
    private final PointService pointService;
    private final EntryService entryService;

    /** 매달 1일 모든 유저에게 포인트 분배 */
    @Scheduled(cron = "0 0 0 1 * *")
    public void giveMonthlyPoint() {
        log.info("Give points to every user start");
        pointService.givePointTo(new GivePointCommand.PointArgument("all", "3"));
        log.info("Give points to every user end");
    }

    /** 매주 월요일 7시, 전주 미션에 대한 벌점 부과 */
    @Scheduled(cron = "0 0 7 * * MON")
    public void imposeFine() {
        log.info("Impose fine start");
        entryService.imposeFine();
        log.info("Impose fine end");
    }
}
