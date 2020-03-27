package com.nokchax.escape.entry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryId implements Serializable {
    private String user;
    private Long mission;
}
