package com.example.mhealth.appointment_diary.Smsretrieverapi;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;

import static com.example.mhealth.appointment_diary.StringSplitter.SplitString.splittedString;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    String myotp="";
    Base64Encoder encoder;
    ProcessMessage pm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        pm=new ProcessMessage();
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            System.out.println("*******running in background****");
            System.out.println(myotp);
            saveReceivedMessage(myotp);

            stopSelf();
        }
    };

    private void saveReceivedMessage(String mess){

        encoder=new Base64Encoder();
        String str=splittedString(mess);
        pm.processReceivedMessage(str);




    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
            if(intent.getStringExtra("otp")!=null) {


                myotp = intent.getStringExtra("otp");

            }
            else{

                myotp="no otp";
            }
        }
        return START_STICKY;
    }

}


