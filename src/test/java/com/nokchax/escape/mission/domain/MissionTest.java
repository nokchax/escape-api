package com.nokchax.escape.mission.domain;

import com.nokchax.escape.mission.repository.MissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("dev")
class MissionTest {
    @Autowired
    private MissionRepository missionRepository;

    /*
     * Hibernate:
     *     select
     *         mission0_.id as id1_1_,
     *         mission0_.end_date_time as end_date2_1_,
     *         mission0_.goal_score as goal_sco3_1_,
     *         mission0_.start_date_time as start_da4_1_
     *     from
     *         mission mission0_
     */
    @Test
    public void findAllTest() {
        System.out.println("=========================================================Before find all");
        missionRepository.findAll();
        System.out.println("=========================================================After find all");
    }

    /*
        Hibernate:
        select
            mission0_.id as id1_1_,
            mission0_.end_date_time as end_date2_1_,
            mission0_.goal_score as goal_sco3_1_,
            mission0_.start_date_time as start_da4_1_
        from
            mission mission0_
        order by
            mission0_.id desc limit ?
     */
    @Test
    @Sql("/missions.sql")
    public void findLatestTest() {
        System.out.println("=========================================================Before find top");
        Mission topByOrderByIdDesc = missionRepository.findTopByOrderByIdDesc();
        System.out.println("=========================================================After find top");

        System.out.println(topByOrderByIdDesc);
    }

    /*
        Hibernate:
        SELECT
            *
        FROM
            mission
        ORDER BY
            id DESC LIMIT 1 OFFSET 1
     */
    @Test
    @Sql("/missions.sql")
    public void findSecondLatestTest() {
        System.out.println("=========================================================Before find last");
        Mission lastMission = missionRepository.findLastMission();
        System.out.println("=========================================================After find last");

        System.out.println(lastMission);
    }
}