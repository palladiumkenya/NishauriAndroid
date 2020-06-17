package com.example.mhealth.appointment_diary.sendmessages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class SendMessage {

    Context ctx;

    public SendMessage(Context ctx){

        this.ctx=ctx;
    }

    public static void sendMessage(String message,String destination){

        try{

            SmsManager sm = SmsManager.getDefault();

            ArrayList<String> parts = sm.divideMessage(message);

            sm.sendMultipartTextMessage(destination, null, parts, null, null);

        }
        catch(Exception e){



        }
    }

    public void sendMessageApi(String message,String destination){

        try{

//            SmsManager sm = SmsManager.getDefault();
//
//            ArrayList<String> parts = sm.divideMessage(message);
//
//            sm.sendMultipartTextMessage(destination, null, parts, null, null);

            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.putExtra("sms_body", message);
            intent.setData(Uri.parse("sms:"+destination));

            if (intent.resolveActivity(ctx.getPackageManager()) != null) {
                ctx.startActivity(intent);
            }
        }
        catch(Exception e){



        }
    }
}
