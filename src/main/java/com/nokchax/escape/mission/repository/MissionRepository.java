package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
    /**
     * 가장 최신의 {@link Mission}을 리턴
     * @return {@link Mission}
     */
    Mission findTopByOrderByIdDesc();


    /**
     * 이전의 * {@link Mission}을 리턴
     * @return {@link Mission}
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM mission ORDER BY id DESC LIMIT 1 OFFSET 1"
    )
    Mission findLastMission();

}
