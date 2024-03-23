package com.mhealth.nishauri.Models;

public class ChatBotQuestion {
    private String question;

    private boolean isSent;

    public ChatBotQuestion(String question) {
        this.question = question;
        this.isSent = isSent;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
