package com.zum.escape.api.users.service;

import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.domain.UserProblemHistory;
import com.zum.escape.api.users.dto.ProblemHistoryDto;
import com.zum.escape.api.users.repository.UserProblemHistoryRepository;
import com.zum.escape.api.users.repository.UserRepository;
import com.zum.escape.api.util.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProblemHistoryService {
    private final UserRepository userRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;

    public List<ProblemHistoryDto> find(Command command) {
        if(command.isArgsEmpty())
            return userProblemHistoryRepository.findAll()
                    .stream()
                    .map(UserProblemHistory::toProblemHistoryDto)
                    .collect(Collectors.toList());

        User user = userRepository.findByLeetcodeName(command.getFirstArg());

        return userProblemHistoryRepository.findAllById(Arrays.asList(user.getEmail()))
                .stream()
                .map(UserProblemHistory::toProblemHistoryDto)
                .collect(Collectors.toList());
    }
}
