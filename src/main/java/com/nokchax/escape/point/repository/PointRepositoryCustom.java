package com.nokchax.escape.point.repository;

import com.nokchax.escape.point.dto.PointDto;

import java.util.List;

public interface PointRepositoryCustom {
    /** 모든 유저의 포인트 정보를 불러옵니다. 포인트 내림차순으로 정렬하여 리턴 */
    List<PointDto> findAllUserPoint();
    /** 조회를 원하는 모든 유저의 포인트 정보를 불러옵니다. 포인트 내림차순으로 정렬하여 리턴 */
    List<PointDto> findUserPointByUserId(List<String> userIds);
    /** 포인트가 0보다 작은 유저의 포인트 정보만 불러옵니다 */
    List<PointDto> findAllPenaltyUsers();
}
