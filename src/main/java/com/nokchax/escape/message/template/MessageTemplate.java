package com.nokchax.escape.message.template;

public abstract class MessageTemplate {
    public String liner() {
        return "----------+---------------";
    }

    public String header() {
        return liner() + "\n" +
                " USERNAME | S   H   M   E \n" +
                liner() + "\n";
    }

    public String body() {
        return "\n";
    }

    public String footer() {
        return liner() + "\n";
    }
}
