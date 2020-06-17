package com.example.mhealth.appointment_diary.PasswordChecker;

public class PasswordCheck {

    public static boolean isTextValid(String mytext) {
        boolean isCorrect = false;
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";
        if (mytext.matches(pattern)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }

        return isCorrect;
    }
}
