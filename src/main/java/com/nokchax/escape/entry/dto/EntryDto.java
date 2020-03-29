package com.nokchax.escape.entry.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntryDto extends MessageTemplate {
    private long missionId;
    private String userId;

    private int score;
    private int hard;
    private int medium;
    private int easy;

    private String getShortenId() {
        return userId.length() > 10 ?
                userId.replaceAll("\\d", "") :
                userId;
    }

    @Override
    public String body() {
        return String.format("%10s|%3d %3d %3d %3d\n", getShortenId(), score, hard, medium, easy);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "mission=" + missionId +
                ", user=" + userId +
                ", score=" + score +
                ", hard=" + hard +
                ", medium=" + medium +
                ", easy=" + easy +
                '}';
    }
}
