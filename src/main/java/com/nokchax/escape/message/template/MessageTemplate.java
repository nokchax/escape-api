package com.nokchax.escape.message.template;

import javax.persistence.Transient;

public abstract class MessageTemplate {
    @Transient
    protected static final char SPLIT_CHAR = '|';
    @Transient
    protected static final String LINER = "----------+---------------";

    public String liner() {
        return LINER;
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
        return "\n" + liner() + "\n";
    }
}
