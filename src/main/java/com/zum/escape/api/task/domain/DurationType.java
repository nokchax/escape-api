package com.zum.escape.api.task.domain;

public enum DurationType {
    DAY(1), WEEK(7), MONTH(30);

    private int day;

    DurationType(int day) {
        this.day = day;
    }
}
