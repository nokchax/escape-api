package com.nokchax.escape.point.repository;

import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.point.domain.PointId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, PointId>, PointRepositoryCustom {
}
