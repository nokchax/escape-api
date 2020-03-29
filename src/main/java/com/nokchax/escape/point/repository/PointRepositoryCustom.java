package com.nokchax.escape.point.repository;

import com.nokchax.escape.point.dto.PointDto;

import java.util.List;

public interface PointRepositoryCustom {
    List<PointDto> findAllUserPointHistoryWithOrdering();
}
