package com.nokchax.escape.mission.domain;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@ToString(exclude = "entry")
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue
    private Long id;
    private int goalScore;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Entry> entry = new LinkedHashSet<>();

    public Mission(long id) {
        this.id = id;
    }

    public boolean isInPeriod(SolvedProblem solvedProblem) {
        return (solvedProblem.getSolvedTime().isBefore(endDateTime)
                && solvedProblem.getSolvedTime().isAfter(startDateTime)) ||
                solvedProblem.getSolvedTime().isEqual(startDateTime) ||
                solvedProblem.getSolvedTime().isEqual(endDateTime);
    }

    public void entry(List<User> users) {
        this.entry = users.stream()
                .map(user -> new Entry(user, this))
                .collect(Collectors.toSet());
    }

    public void updateEntry(List<User> newUsers) {
        newUsers.forEach(newUser -> entry.add(new Entry(newUser, this)));
    }
}
