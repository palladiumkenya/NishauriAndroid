package com.mhealth.nishauri.Models;

public class FAQ {
    private String question;
    private String answer;
    public boolean expanded = false;
    public boolean parent = false;


    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}