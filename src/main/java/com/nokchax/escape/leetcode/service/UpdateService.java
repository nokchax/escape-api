package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateService {
    private final UserService userService;
    private final EntryService entryService;

    public List<EntryDto> updateLatestMission(UpdateCommand.UpdateArgument argument) {
        List<User> users = userService.findByArgument(argument);

        // update logic
        /*
            1. user page 크롤후 비교 -> 푼 총 문제수에 변화가 없다면 업데이트 스킵 후 리턴
                1-2. 푼 총 문제수에 변화가 있다면, 페이지 크롤후 푼문제 업데이트
                1-3. 문제 업데이트 이후 회원의 solve problem 수 업데이트
                1-4. 업데이트된 problem 수와 크롤한 문제수가 다르다면? 3번으로
            2. api 크롤후 비교 -> 여기까지 왔다면 일단 푼 문제수가 변화가 있던건 것이니까 2-2로 //아니다... api를 그냥 크롤하면 문제 리스트만 보여준다....
                2-2. 문제 업데이트 이후 회원의 solve problem 수 업데이트
                2-3. 업데이트된 problem 수와 크롤한 문제수가 다르면? 3번으로
            3. login api 크롤후 비교
                3-2. 문제 업데이트 이후 회원의 solve problem 수 업데이트
                3-3. 여기까지 오면 problem 수와 크롤한 문제수가 같을것.
            4. entry return 후 종료
         */

        return entryService.findAllUserInLatestMission(users);
    }

    public List<ProblemDto> updateProblems() {
        /*
            1. api 크롤해서 업데이트 (로그인 크롤)
            2. 기존 problem과 새 문제들 비교 후 업데이트 된 문제들 저장
            3. 업데이트 된 문제들 모두 리턴.
         */

        return Collections.EMPTY_LIST;
    }
}
