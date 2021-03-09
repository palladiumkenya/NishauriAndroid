package com.example.mhealth.appointment_diary.Datecalculator;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 3/14/18.
 */

public class DateCalculator {

    Context ctx;

    public DateCalculator(Context ctx) {
        this.ctx = ctx;
    }

    public DateCalculator() {
    }

    public boolean checkDateDifferenceWithCurrentDate(String mdate){
        boolean isCorrect=false;

        try{

            DateFormat df=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date dt=new Date();
            String currentDate=df.format(dt).toString();


            long mydff=calculateDateDifference(currentDate,mdate);
            if(mydff>0){
                isCorrect=true;
            }
            else{

                isCorrect=false;
            }
            return isCorrect;

        }
        catch(Exception e){

            isCorrect=false;

            return isCorrect;
        }
    }




    public boolean checkDateDifferenceWithTwoDates(String date1,String date2){
        boolean isCorrect=false;

        try{

            long mydff=calculateDateDifference(date1,date2);
            if(mydff>=0){
                isCorrect=true;
            }
            else{

                isCorrect=false;
            }
            return isCorrect;

        }
        catch(Exception e){

            isCorrect=false;

            return isCorrect;
        }
    }


    public Long calculateDateDifference(String date1,String date2){

        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);


        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            return diffDays;




        }
        catch(Exception e){

            return null;

        }
    }
}

