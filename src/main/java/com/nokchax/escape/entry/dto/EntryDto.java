package com.nokchax.escape.entry.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public EntryDto(long missionId, String userId, int score, int hard, int medium, int easy) {
        this.missionId = missionId;
        this.userId = userId;
        this.score = score;
        this.hard = hard;
        this.medium = medium;
        this.easy = easy;
    }

    private String getShortenId() {
        return userId.length() > 10 ?
                userId.replaceAll("\\d", "") :
                userId;
    }

    @Override
    public String title() {
        return "    ID    | S   H   M   E \n";
    }

    @Override
    public String body() {
        return String.format("%10s|%3d %3d %3d %3d\n", getShortenId(), score, hard, medium, easy);
    }
}
