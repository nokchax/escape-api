package com.zum.escape.api.task.domain;

public enum Duration {
    DAY(1), WEEK(7), MONTH(30);

    private int day;

    Duration(int day) {
        this.day = day;
    }
}
