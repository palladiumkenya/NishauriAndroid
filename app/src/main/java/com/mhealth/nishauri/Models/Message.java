package com.mhealth.nishauri.Models;

public class Message {
    private String message;
    private boolean isSender;

    public Message(String message, boolean isSender) {
        this.message = message;
        this.isSender = isSender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSender() {
        return isSender;
    }
}
