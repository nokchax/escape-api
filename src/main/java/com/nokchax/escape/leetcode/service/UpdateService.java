package com.nokchax.escape.leetcode.service;

import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateService {
    private final UserService userService;
    private final EntryService entryService;

    public List<EntryDto> updateLatestMission(UpdateCommand.UpdateArgument argument) {
        List<User> users = userService.findByArgument(argument);

        return entryService.findAllUserInLatestMission(users);
    }
}
