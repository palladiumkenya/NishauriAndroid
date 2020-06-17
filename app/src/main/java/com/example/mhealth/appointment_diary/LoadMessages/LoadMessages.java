package com.example.mhealth.appointment_diary.LoadMessages;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.tables.Tracers;
import com.example.mhealth.appointment_diary.tables.Trccheck;
import com.example.mhealth.appointment_diary.tables.notOkWellnessMessage;
import com.example.mhealth.appointment_diary.tables.unrecognisedWellnessMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LoadMessages {

    Context ctx;
    Base64Encoder encoder;


    public LoadMessages(Context ctx) {
        this.ctx = ctx;
        encoder=new Base64Encoder();

    }



    public void loadInboxMessages() {
        try {

            int count=0;
            ContentResolver contentResolver = ctx.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),null, null, null,null);
            int indexBody = smsInboxCursor.getColumnIndex("body");

            int indexDate = smsInboxCursor.getColumnIndex("date");



            System.out.println("***********message****::");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String addr = smsInboxCursor.getString(2);
                String datee = smsInboxCursor.getString(indexDate);
                Long mydate=Long.parseLong(datee);

                if(addr.contentEquals(Config.mainShortcode)){


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    String mytimestamp=formatter.format(calendar.getTime());

                    count++;

                    String[] originalArray=str.split("\\*");

                    if(originalArray.length>0){

                        String messageIdentifier=originalArray[0];
                        String messageString1="";
                        String messageString="";

//                        start logic to process and save tracers

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

                        else if(messageIdentifier.equalsIgnoreCase("TRCVAL")){

                            messageString1=originalArray[1];
                            messageString=encoder.decryptedString(messageString1);
                            Trccheck.deleteAll(Trccheck.class);

                            Trccheck pwm=new Trccheck(messageString);
                            pwm.save();

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



                    if(str.contains("TOAPP")){
                        System.out.println("***********decrypted mess***********");

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
                        String fileSerial=decryptedmessPieces[5];
                        String informantnumber=decryptedmessPieces[6];


                        List<Appointments> myl = Appointments.find(Appointments.class, "appointmentid=?", appointmentid);

                        if(myl.isEmpty())
                        {


                            Appointments app=new Appointments(ccnumber,name,phone,apptype,mytimestamp,"unread",appointmentid,"false",fileSerial,informantnumber,"-1",mytimestamp,"0");
                            app.save();
                        }
                        else
                        {

                        }


                    }




                }



            } while (smsInboxCursor.moveToNext());

        }
        catch(Exception e){

        }


    }




}
