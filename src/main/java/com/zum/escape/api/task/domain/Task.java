package com.zum.escape.api.task.domain;

import com.zum.escape.api.users.domain.User;
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
public class Task {
    @Id @GeneratedValue
    private Long id;
    private int goalScore = 5;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    private Duration durationType;
    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    private List<TaskParticipant> participants = new ArrayList<>();

    public void registerParticipants(List<User> users) {
        List<TaskParticipant> participants = new ArrayList<>();

        for(User user : users) {
            participants.add(new TaskParticipant(this, user));
        }

        this.participants = participants;
    }

    public void updateParticipants(List<User> newUsers) {
        for(User user : newUsers) {
            boolean insertable = true;
            for(TaskParticipant participant : participants) {
                if(user.getId().equalsIgnoreCase(participant.getUsers().getId())) {
                    insertable = false;
                    break;
                }
            }

            if(insertable) {
                participants.add(new TaskParticipant(this, user));
            }
        }
    }
}
