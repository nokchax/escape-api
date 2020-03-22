package com.nokchax.escape.mission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//    @Enumerated(EnumType.STRING)
//    private Duration durationType;
    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    private List<Entry> participants = new ArrayList<>();
}
