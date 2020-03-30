package com.nokchax.escape.message.template;

public abstract class MessageTemplate {
    public String liner() {
        return "----------+---------------\n";
    }

    public abstract String title();

    public String header() {
        return liner() +
                title() +
                liner();
    }

    public abstract String body();

    public String footer() {
        return liner();
    }
}
