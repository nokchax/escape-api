package com.nokchax.escape.mission.dto;

import com.nokchax.escape.mission.domain.Entry;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.mission.domain.QEntry;
import com.nokchax.escape.mission.domain.QMission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MissionEntryDto {
    private Mission mission;
    private Entry entry;
}
