package com.nokchax.escape.mission.repository;

import com.nokchax.escape.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}
