package com.zum.escape.api.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskParticipantId implements Serializable {
    private String users;
    private Long tasks;
}
