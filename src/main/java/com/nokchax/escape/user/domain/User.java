package com.nokchax.escape.user.domain;

import com.nokchax.escape.command.RegisterTelegramIdCommand;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.problem.domain.SolvedProblem;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@ToString(exclude = {"solvedProblem"})
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    // 컬럼을 삭제하고, 그때그때 서브 쿼리로 갯수를 가져오는게 좋지 않을까
    private int solvedProblemCount;
    @Column(unique = true)
    private String telegramId;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<SolvedProblem> solvedProblem = new HashSet<>();

    public User(String id, String password, String name) {
        checkInput(id, password, name);

        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User(String id) {
        this.id = id;
    }

    public boolean isNotUpdated(CrawledUserInfo crawledUserInfo) {
        return solvedProblemCount == crawledUserInfo.getSolvedProblemCount();
    }

    private void checkInput(String... inputs) {
        Arrays.stream(inputs)
                .forEach(input -> {
                    if(StringUtils.isEmpty(input)) {
                        throw new IllegalArgumentException(
                                "parameter is not correct, command like below form\n" +
                                "/register -u {id} -p {password} -n {name}"
                        );
                    }
                });
    }

    public boolean addSolvedProblems(Set<SolvedProblem> notSavedSolvedProblems) {
        solvedProblemCount += notSavedSolvedProblems.size();

        return solvedProblem.addAll(notSavedSolvedProblems);
    }

    public void updateTelegramId(RegisterTelegramIdCommand.UpdateTelegramIdArgument updateTelegramIdArgument) {
        if(StringUtils.isEmpty(updateTelegramIdArgument.getTelegramId())) {
            throw new IllegalArgumentException("Telegram id is empty or null");
        }

        this.telegramId = updateTelegramIdArgument.getTelegramId();
    }
}
