package com.nokchax.escape.problem.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemId implements Serializable {
    private String user;
    private Long problem;
    private Long mission;
}
