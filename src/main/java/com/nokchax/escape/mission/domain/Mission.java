package com.nokchax.escape.mission.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString(exclude = {"participants"})
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
    private List<Entry> participants = new ArrayList<>();
}
