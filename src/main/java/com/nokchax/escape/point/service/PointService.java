package com.nokchax.escape.point.service;

import com.nokchax.escape.command.GivePointCommand;
import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.point.dto.PointDto;
import com.nokchax.escape.point.repository.PointRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    /** 사용자에게 포인트를 주거나 뺏는다, 대상 아이디를 쓰면 해당 유저만, all 이라면 모든 유저에게 */
    public List<PointDto> givePointTo(GivePointCommand.PointArgument pointArgument) {
        List<User> users = userRepository.findByUserId(pointArgument.getTargetUser());

        pointRepository.saveAll(userToPoint(pointArgument, users));

        return pointRepository.findUserPointByUserId(extractUserIds(users));
    }

    private List<Point> userToPoint(GivePointCommand.PointArgument pointArgument, List<User> users) {
        return users.stream()
                    .map(user -> (new Point(user, pointArgument.getPoint())))
                    .collect(Collectors.toList());
    }

    private List<String> extractUserIds(List<User> users) {
        return  users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

}
