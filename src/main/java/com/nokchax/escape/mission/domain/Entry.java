package com.nokchax.escape.mission.domain;

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
