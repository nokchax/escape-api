package com.zum.escape.api.users.dto;

public abstract class Message {
    public abstract  String makeHeader();
    public abstract String makeFooter();
    public abstract String toMessage();
}
