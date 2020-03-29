package com.nokchax.escape.user.dto;

import com.nokchax.escape.message.template.MessageTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPointDto extends MessageTemplate {
    private String userId;
    private int point;
}
