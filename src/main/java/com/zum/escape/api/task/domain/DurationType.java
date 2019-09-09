package com.zum.escape.api.task.domain;

/**
 * Created by greentea@zuminternet.com on 2019-09-09
 */
public enum DurationType {
    DAY(1), WEEK(7), MONTH(30);

    private int day;

    DurationType(int day) {
        this.day = day;
    }
}
