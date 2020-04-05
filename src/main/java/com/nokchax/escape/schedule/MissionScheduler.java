package com.nokchax.escape.schedule;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.leetcode.service.UpdateService;
import com.nokchax.escape.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionScheduler {
    private final MissionService missionService;
    private final UpdateService updateService;

    //주간 미션 생성

    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void createTask() {
        log.info("Create mission start");
        // 이전 entry 업데이트
        updateService.updateLatestMission(UpdateCommand.UpdateArgument.UPDATE_ALL_ARGUMENT);
        // 새 미션 생성
        missionService.createMission();
        log.info("Create mission end");
    }
}
