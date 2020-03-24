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
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int score;
    private int hard;
    private int medium;
    private int easy;
}
