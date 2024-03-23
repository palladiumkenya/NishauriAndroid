package com.mhealth.nishauri.Models;

public class BotChatMessage {

    private String msg;
    private String question;
    private boolean isSent;

    public BotChatMessage(String message, boolean isSent) {
        this.msg = message;
        this.isSent = isSent;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSent() {
        return isSent;
    }
}
