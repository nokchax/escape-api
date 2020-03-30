package com.nokchax.escape.point.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto extends MessageTemplate {
    private String userId;
    private int point;


    @Override
    public String liner() {
        return "--------------+-------\n";
    }

    @Override
    public String title() {
        return "      ID      | POINT \n";
    }

    @Override
    public String body() {
        return String.format(" %12s | %5d \n", userId, point);
    }
}
