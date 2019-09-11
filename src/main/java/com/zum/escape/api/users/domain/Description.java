package com.zum.escape.api.users.domain;

import lombok.Getter;

@Getter
public enum Description {
    FINE("벌금 부과"),
    PAY_A_FINE("벌금 납부"),
    PROVIDE_POINT("포인트 지급"),
    USE_POINT("포인트 사용");

    private String desc;

    Description(String desc) {
        this.desc = desc;
    }
}
