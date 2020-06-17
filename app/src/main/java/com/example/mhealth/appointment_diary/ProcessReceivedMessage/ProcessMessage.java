package com.example.mhealth.appointment_diary.ProcessReceivedMessage;

import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.tables.Assignedtracers;
import com.example.mhealth.appointment_diary.tables.Tracers;
import com.example.mhealth.appointment_diary.tables.Trccheck;
import com.example.mhealth.appointment_diary.tables.notOkWellnessMessage;
import com.example.mhealth.appointment_diary.tables.unrecognisedWellnessMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProcessMessage {


    Base64Encoder encoder;

    public ProcessMessage() {
        encoder=new Base64Encoder();
    }

    public void processReceivedMessage(String str){


        //start code to get wellness messages


        String[] originalArray=str.split("\\*");
        if(originalArray.length>0){

            String messageIdentifier=originalArray[0];
            String messageString1="";
            String messageString="";

//                      start logic to process and save tracers

            if(messageIdentifier.equalsIgnoreCase("TRC")){

                messageString1=originalArray[1];
                messageString=encoder.decryptedString(messageString1);

                String[] splittedMessage=messageString.split("\\*");

                String fname=splittedMessage[0];
                String mname=splittedMessage[1];
                String phone=splittedMessage[2];
                String userid=splittedMessage[3];
                List<Tracers> myl=Tracers.findWithQuery(Tracers.class,"select * from Tracers where userid=?",userid);
                if(myl.size()>0){

                }
                else{
                    Tracers tr=new Tracers(fname,mname,phone,userid);
                    tr.save();

                }



            }


            else if(messageIdentifier.equalsIgnoreCase("TRCVAL")){

                messageString1=originalArray[1];
                messageString=encoder.decryptedString(messageString1);
                Trccheck.deleteAll(Trccheck.class);

                Trccheck pwm=new Trccheck(messageString);
                pwm.save();

            }

//                      end logic to process and save tracers

            else if(messageIdentifier.equalsIgnoreCase("NEG")){

                messageString1=originalArray[1];
                messageString=encoder.decryptedString(messageString1);

                String[] splittedMessage=messageString.split("\\*");

                String msgid=splittedMessage[4];
                String ccno=splittedMessage[0];
                String fname=splittedMessage[1];
                String phoneno=splittedMessage[2];
                String msg=splittedMessage[3];
                List<notOkWellnessMessage> myl=notOkWellnessMessage.findWithQuery(notOkWellnessMessage.class,"select * from not_ok_wellness_message where msgid=?",msgid);
                if(myl.size()==0){

                    notOkWellnessMessage pwm=new notOkWellnessMessage(ccno,fname,phoneno,msg,msgid,"false");
                    pwm.save();
                    System.out.println(encoder.decryptedString(messageString)+"*POSITIVE ");

                }

            }

            else if(messageIdentifier.equalsIgnoreCase("UNR")){

                messageString1=originalArray[1];
                messageString=encoder.decryptedString(messageString1);

                String[] splittedMessage=messageString.split("\\*");

                String msgid=splittedMessage[4];
                String ccno=splittedMessage[0];
                String fname=splittedMessage[1];
                String phoneno=splittedMessage[2];
                String msg=splittedMessage[3];
                List<unrecognisedWellnessMessage> myl=unrecognisedWellnessMessage.findWithQuery(unrecognisedWellnessMessage.class,"select * from unrecognised_wellness_message where msgid=?",msgid);
                if(myl.size()==0){

                    unrecognisedWellnessMessage pwm=new unrecognisedWellnessMessage(ccno,fname,phoneno,msg,msgid,"false");
                    pwm.save();
                    System.out.println(encoder.decryptedString(messageString)+"*Unrecognised ");

                }

            }



        }


        //end code to get wellness messages

        if(str.contains("TOAPP")){

            System.out.println("******************mess toapp*****************");
            String[] decryptedPiece=str.split("\\*");
            String messToDec=decryptedPiece[1];


            String decryptedmess = Base64Encoder.decryptedString(messToDec);
            System.out.println(decryptedmess);

            String[] decryptedmessPieces=decryptedmess.split("\\*");

            String ccnumber=decryptedmessPieces[0];
            String name=decryptedmessPieces[1];
            String phone=decryptedmessPieces[2];
            String apptype=decryptedmessPieces[3];
            String appointmentid=decryptedmessPieces[4];
            String fileserial=decryptedmessPieces[5];
            String informantnumber=decryptedmessPieces[6];
            String messdate=decryptedmessPieces[7];


            List<Appointments> myl = Appointments.find(Appointments.class, "appointmentid=?", appointmentid);
            List<Assignedtracers> myltracers = Assignedtracers.find(Assignedtracers.class, "appointmentid=?", appointmentid);

            if(myl.isEmpty())
            {

                //if the appointment was assigned a tracer
                if(myltracers.size()>0){

                    //loop through tracers to get the tracer username from the specified apppointment id

                    for(int xt=0;xt<myltracers.size();xt++){

                        String tracerUname=myltracers.get(xt).getUsername();

                        Appointments app=new Appointments(ccnumber,name,phone,apptype,messdate,"unread",appointmentid,"true",fileserial,informantnumber,tracerUname,messdate,"0");
                        app.save();


                    }



                }
                //if appointment dint have a tracer
                else{

                    Appointments app=new Appointments(ccnumber,name,phone,apptype,messdate,"unread",appointmentid,"false",fileserial,informantnumber,"-1",messdate,"0");
                    app.save();

                }


            }
            else
            {

            }




        }

    }
}
