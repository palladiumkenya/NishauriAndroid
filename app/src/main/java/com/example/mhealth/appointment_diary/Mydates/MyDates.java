package com.example.mhealth.appointment_diary.Mydates;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDates {

    static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    public static String getCurrentYear(){

        try{

            String currentArray[]=timeStamp.split("\\.");
            String currentYear=currentArray[0];

            return currentYear;

        }
        catch(Exception e){

            return "";

        }
    }




    public static String getCurrentMonth(){

        try{

            String currentArray[]=timeStamp.split("\\.");
            String currentMonth=currentArray[1];
            return currentMonth;

        }
        catch(Exception e){

            return "";

        }
    }

    public static String getCurrentDate(){

        try{

            String currentArray[]=timeStamp.split("\\.");
            String currentDate=currentArray[2];

            return currentDate;

        }
        catch(Exception e){

            return "";

        }
    }


}
