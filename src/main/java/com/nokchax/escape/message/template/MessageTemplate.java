package com.nokchax.escape.message.template;

public abstract class MessageTemplate {
    public String liner() {
        return "----------+---------------";
    }

    public abstract String title();

    public String header() {
        return liner() + "\n" +
                title() + "\n" +
                liner() + "\n";
    }

    public abstract String body();

    public String footer() {
        return liner() + "\n";
    }
}
