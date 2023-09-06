package com.mhealth.nishauri.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.ChatInterface;
import com.mhealth.nishauri.Activities.MainActivity;

public class ScreenLockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            // The screen is locked, perform logout action here.
            // This is where you should log out the user.

            /*String endPoint = Stash.getString(Constants.AUTH_TOKEN);
            Stash.clearAll();

            Stash.put(Constants.AUTH_TOKEN, endPoint);*/
            //Toast.makeText(context, "Please Enter a Question", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, LoginActivity.class));

            //Intent intent1 = new Intent(context, LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //context.startActivity(intent1);
        }
    }
}
