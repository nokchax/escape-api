package com.zum.escape.api.users.repository;

import com.zum.escape.api.users.domain.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointRepository extends JpaRepository<UserPoint, String> {
    //모든 유저의 포인트 조회
    List<UserPoint> findAllByOrderByPointDesc();
    //포인트가 -인 사람들만 조회
    List<UserPoint> findAllByPointIsLessThanOrderByPointAsc(int point);
}
