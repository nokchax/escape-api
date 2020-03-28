package com.nokchax.escape.entry.domain;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(EntryId.class)
public class Entry {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int score;
    private int hard;
    private int medium;
    private int easy;

    public Entry(SolvedProblemSummaryDto solvedProblemSummaryDto) {
        this.mission = new Mission(solvedProblemSummaryDto.getMissionId());
        this.user = new User(solvedProblemSummaryDto.getUserId());
        this.score = solvedProblemSummaryDto.evaluateScore();
        this.hard = solvedProblemSummaryDto.getHardCount();
        this.medium = solvedProblemSummaryDto.getMediumCount();
        this.easy = solvedProblemSummaryDto.getEasyCount();
    }


    public boolean isMissionSuccess(int goalScore) {
        return this.score >= goalScore;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "mission=" + mission.getId() +
                ", user=" + user.getId() +
                ", score=" + score +
                ", hard=" + hard +
                ", medium=" + medium +
                ", easy=" + easy +
                '}';
    }
}
