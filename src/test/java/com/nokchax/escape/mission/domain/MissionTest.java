package com.nokchax.escape.mission.domain;

import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

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
    public void findSecondLatestTest() {
        System.out.println("=========================================================Before find last");
        Mission lastMission = missionRepository.findLastMission();
        System.out.println("=========================================================After find last");

        System.out.println(lastMission);
    }

    /*
        =========================================================Before find latest
        Hibernate:
            select
                mission0_.id as id1_1_0_,
                entry1_.mission_id as mission_1_0_1_,
                entry1_.user_id as user_id2_0_1_,
                mission0_.end_date_time as end_date2_1_0_,
                mission0_.goal_score as goal_sco3_1_0_,
                mission0_.start_date_time as start_da4_1_0_,
                entry1_.easy as easy3_0_1_,
                entry1_.hard as hard4_0_1_,
                entry1_.medium as medium5_0_1_,
                entry1_.score as score6_0_1_,
                entry1_.mission_id as mission_1_0_0__,
                entry1_.user_id as user_id2_0_0__
            from
                mission mission0_
            inner join
                entry entry1_
                    on mission0_.id=entry1_.mission_id
            where
                mission0_.id=(
                    select
                        max(mission2_.id)
                    from
                        mission mission2_
                )
        =========================================================After find latest
        Mission{id=29, goalScore=5, startDateTime=2020-03-23T00:00, endDateTime=2020-03-29T23:59:59}
     */
    @Test
    public void findLatestMissionTest() {
        System.out.println("=========================================================Before find latest");
        Mission latestMission = missionRepository.findLatestMissionWithEntry();
        System.out.println("=========================================================After find latest");

        System.out.println(latestMission);
        latestMission.getEntry().forEach(System.out::println);
        assertThat(latestMission.getId()).isEqualTo(29);
    }

    @Test
    public void findLatestMissionWithEntryAndUserTest() {
        System.out.println("=========================================================Before find latest");
        Mission latestMission = missionRepository.findMissionWithEntryAndUser();
        System.out.println("=========================================================After find latest");

        System.out.println(latestMission);
        latestMission.getEntry().forEach(System.out::println);
        assertThat(latestMission.getId()).isEqualTo(29);
        latestMission.getEntry()
                .stream()
                .map(Entry::getUser)
                .map(User::getSolvedProblem)
                .forEach(System.out::println);
    }
}