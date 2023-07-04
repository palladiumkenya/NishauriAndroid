package com.mhealth.nishauri;

import android.app.Notification;
import android.app.Service;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mhealth.nishauri.Models.DateTable;

import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    int z;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        try{
            List<DateTable> dateTable =DateTable.findWithQuery(DateTable.class, "SELECT *from DATE_TABLE ORDER BY id DESC LIMIT 1");
            if (dateTable.size()==1){
                for (int x=0; x<dateTable.size(); x++){
                    z=dateTable.get(x).getAppointmentDate();
                }
            }

        } catch(Exception e){

        }


        /*if(z==61){
        getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());}
    }*/

    /*public void getFirebaseMessage(String title, String msg){
        Notification.Builder builder = new Notification.Builder(this, "myFirebaseChannel")
                .setSmallIcon(R.drawable.ic__notifications_24)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(101, builder.build());



    }*/
}}
