package com.example.mhealth.appointment_diary.defaulters_diary.missed.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mhealth.appointment_diary.appointment_diary.TodaysAppointment;

/**
 * Created by abdullahi on 11/15/2017.
 */

public class MissedCallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "my report alarm called", Toast.LENGTH_SHORT).show();
////        sendReadReport();
//        intent = new Intent();
//        intent.setClass(context, Mylogin.class); //Test is a dummy class name where to redirect
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.putExtra("msg", str);
//        context.startActivity(intent);
//        finish();
//        sendReadReport();



        intent = new Intent();
        intent.setClass(context, MissedCallFragment.class); //Test is a dummy class name where to redirect
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("msg", "my alarm");
        context.startActivity(intent);

    }
}
