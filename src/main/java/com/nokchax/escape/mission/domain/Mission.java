package com.nokchax.escape.mission.domain;

import com.nokchax.escape.entry.domain.Entry;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue
    private Long id;
    private int goalScore = 5;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Entry> entry = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", goalScore=" + goalScore +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
