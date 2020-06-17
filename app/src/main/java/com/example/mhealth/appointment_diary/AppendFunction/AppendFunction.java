package com.example.mhealth.appointment_diary.AppendFunction;

public class AppendFunction {


    public static String AppendUniqueIdentifier(String value){

        try{
            String newValue="";

            if(value.length()==1){

                newValue="0000"+value;
            }
            else if(value.length()==2){

                newValue="000"+value;
            }
            else if(value.length()==3){

                newValue="00"+value;
            }
            else if(value.length()==4){

                newValue="0"+value;
            }
            else{

                newValue=value;
            }

            return newValue;
        }
        catch(Exception e){

            return "";
        }
    }
}
