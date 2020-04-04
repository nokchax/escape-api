package com.nokchax.escape.schedule;

import com.nokchax.escape.command.GivePointCommand;
import com.nokchax.escape.mission.service.MissionService;
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
    private final MissionService missionService;

    /** 매달 1일 모든 유저에게 포인트 분배 */
    @Scheduled(cron = "0 0 0 1 * *")
    public void giveMonthlyPoint() {
        log.info("Give points to every user start");
        pointService.givePointTo(new GivePointCommand.PointArgument("all", "3"));
        log.info("Give points to every user end");
    }

    /** 매 미션마다 벌점 부과 */
    @Scheduled(cron = "30 1 0 * * MON")
    public void imposeFine() {
        /*
            끝나는 시점에 바로 부과할 필요는 없다.
            매주 월요일 아침에 부과한다.
            지난주 mission 참가자 중 미션 통과 못한 사람 불러오기
         */


    }
}
