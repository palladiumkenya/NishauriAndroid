package com.example.mhealth.appointment_diary.StringSplitter;

public class SplitString {

    public static String splittedString(String x){

//        String x="<#> test message here +11AKmxGULS";
        String newstring=substringer(x, "<#");
        String strTrimmed=newstring.trim();
        System.out.println(strTrimmed);

        String firstWords = strTrimmed.substring(0, strTrimmed.lastIndexOf(" "));
        // String finalString= strTrimmed.substring(strTrimmed.lastIndexOf(" ",strTrimmed.length()));
        return firstWords;



    }

    public static String substringer(String inputString, String remove) {
        if (inputString.substring(0, remove.length()).equalsIgnoreCase(remove)) {
            return inputString.substring(remove.length()).trim();
        }
        else {
            return inputString.trim();
        }
    }


}
